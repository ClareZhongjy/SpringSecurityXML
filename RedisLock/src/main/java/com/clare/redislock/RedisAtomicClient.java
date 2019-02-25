package com.clare.redislock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Collections;
import java.util.List;

/**
 * @author liuxu
 * @ProjectName ems-payment-service
 * @PackageName cn.com.crc.redis
 * @ClassName RedisAtomicClient
 * @date 2018/10/22 15:06
 * @since JDK 1.8
 */
public class RedisAtomicClient {

  private static final Logger logger = LoggerFactory.getLogger(RedisAtomicClient.class);

  private static final String CONSTR_KEY="_REV_REDIS_LOCK";


  private RedisTemplate redisTemplate;

  private StringRedisTemplate stringRedisTemplate;

  //lua脚本枷锁
  private static final String INCR_BY_WITH_TIMEOUT = "local v; v = redis.call('incrBy',KEYS[1],ARGV[1]); if tonumber(v) == 1 then\n redis.call('expire',KEYS[1],ARGV[2])\n end\n return v";

  //lua脚本删除锁
  private static final String COMPARE_AND_DELETE = "if redis.call('get',KEYS[1]) == ARGV[1]\n then\n     return redis.call('del',KEYS[1])\n else\n     return 0\n end";


  public RedisAtomicClient(RedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
    this.stringRedisTemplate = new StringRedisTemplate();
    this.stringRedisTemplate.setConnectionFactory(redisTemplate.getConnectionFactory());
    this.stringRedisTemplate.afterPropertiesSet();
  }

  public RedisAtomicClient(RedisTemplate redisTemplate, StringRedisTemplate stringRedisTemplate) {
    this.redisTemplate = redisTemplate;
    this.stringRedisTemplate =stringRedisTemplate;
  }

  /**
   * 获取redis的分布式锁，内部实现使用了redis的setnx。只会尝试一次，如果锁定失败返回null，如果锁定成功则返回RedisLock对象，调用方需要调用RedisLock.unlock()方法来释放锁.
   * <br/>使用方法：
   * <pre>
   * RedisLock lock = redisAtomicClient.getLock(key, 2);
   * if(lock != null){
   *      try {
   *          //lock succeed, do something
   *      }finally {
   *          lock.unlock();
   *      }
   * }
   * </pre>
   * 由于RedisLock实现了AutoCloseable,所以可以使用更简介的使用方法:
   * <pre>
   *  try(RedisLock lock = redisAtomicClient.getLock(key, 2)) {
   *      if (lock != null) {
   *          //lock succeed, do something
   *      }
   *  }
   * </pre>
   *
   * @param key           要锁定的key
   * @param expireSeconds key的失效时间
   * @return 获得的锁对象（如果为null表示获取锁失败），后续可以调用该对象的unlock方法来释放锁.
   */
  public RedisLock getLock(final String key, long expireSeconds) {
    return getLock(key, expireSeconds, 0, 0);
  }

  /**
   * 获取redis的分布式锁，内部实现使用了redis的setnx。如果锁定失败返回null，如果锁定成功则返回RedisLock对象，调用方需要调用RedisLock.unlock()方法来释放锁
   * <br/>
   * <span style="color:red;">此方法在获取失败时会自动重试指定的次数,由于多次等待会阻塞当前线程，请尽量避免使用此方法</span>
   *
   * @param key                     要锁定的key
   * @param expireSeconds           key的失效时间
   * @param maxRetryTimes           最大重试次数,如果获取锁失败，会自动尝试重新获取锁；
   * @param retryIntervalTimeMillis 每次重试之前sleep等待的毫秒数
   * @return 获得的锁对象（如果为null表示获取锁失败），后续可以调用该对象的unlock方法来释放锁.
   */
  public RedisLock getLock(final String key, final long expireSeconds, int maxRetryTimes, long retryIntervalTimeMillis) {
    final String setKey = key+ CONSTR_KEY;
    final String value = setKey.hashCode() + "";
    int maxTimes = maxRetryTimes + 1;
    logger.debug("lejianTest:---setKey:"+setKey+"---value"+value+"---maxTimes:"+maxTimes);
    for (int i = 0; i < maxTimes; i++) {
      System.out.println("==========key==="+setKey.getBytes());
      System.out.println("==========value==="+value.getBytes());

          /*  Boolean flag = stringRedisTemplate.getConnectionFactory().getConnection().setNX(setKey.getBytes(),value.getBytes());
            stringRedisTemplate.expire(setKey,expireSeconds, TimeUnit.SECONDS);
            String status = null;
            if(flag) {
                status = "OK";
            }*/
      String status = stringRedisTemplate.execute(new RedisCallback<String>() {
        @Override
        public String doInRedis(RedisConnection connection) throws DataAccessException {
          Object nativeConnection = connection.getNativeConnection();
          String status = null;
          if (nativeConnection instanceof JedisCluster) {
            logger.debug("JedisCluster1:---setKey:"+setKey+"---value"+value+"---maxTimes:"+expireSeconds);
            status = ((JedisCluster) nativeConnection).set(setKey, value, "NX", "EX", expireSeconds);
            logger.debug("JedisCluster:---status:"+status);
          }
          if (nativeConnection instanceof Jedis) {
            logger.debug("Jedis1:---setKey:"+setKey+"---value"+value+"---maxTimes:"+expireSeconds);
            status = ((Jedis) nativeConnection).set(setKey, value, "NX", "EX", expireSeconds);
            logger.debug("Jedis:---status:"+status);
          }
          return status;
        }
      });
      logger.debug("getLock:---status:"+status);
      if ("OK".equals(status)) {//抢到锁
        return new RedisLockInner(stringRedisTemplate, setKey, value);
      }
      if (retryIntervalTimeMillis > 0) {
        try {
          Thread.sleep(retryIntervalTimeMillis);
        } catch (InterruptedException e) {
          logger.error("RedisAtomicClient.getLock error",e);
          break;
        }
      }
      if (Thread.currentThread().isInterrupted()) {
        break;
      }
    }
    return null;
  }

  private class RedisLockInner implements RedisLock{

    private StringRedisTemplate stringRedisTemplate;
    private String key;
    private String expectedValue;

    protected RedisLockInner(StringRedisTemplate stringRedisTemplate, String key, String expectedValue){
      this.stringRedisTemplate = stringRedisTemplate;
      this.key = key;
      this.expectedValue = expectedValue;
    }


    /**
     * 释放redis分布式锁
     */
    @Override
    public boolean unlock() {
      List<String> keys = Collections.singletonList(key);
      List<String> valuesList = Collections.singletonList(expectedValue);
      logger.debug("lejianTest5:---keys:"+keys);
      //stringRedisTemplate.execute(new DefaultRedisScript<>(COMPARE_AND_DELETE, String.class), keys, expectedValue);
      boolean flag = stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
        Object nativeConnection = connection.getNativeConnection();
        Long result = 0L;
        if (nativeConnection instanceof JedisCluster) {    // 集群
          logger.debug("unlock:---keys:"+keys);
          result = (Long) ((JedisCluster) nativeConnection).eval(COMPARE_AND_DELETE, keys, valuesList);
          logger.debug("unlock:---keys:"+keys+"====result"+result);
        }
        if (nativeConnection instanceof Jedis) {         // 单机
          logger.debug("unlock:---keys:"+keys);
          result = (Long) ((Jedis) nativeConnection).eval(COMPARE_AND_DELETE, keys, valuesList);
          logger.debug("unlock:---keys:"+keys+"====result"+result);
        }
        return result == 1L;
      });
      return flag;
    }

    @Override
    public void close() throws Exception {
      this.unlock();
    }
  }
}
