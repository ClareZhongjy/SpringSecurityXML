package com.clare.redislock;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.List;
import java.util.Vector;

public class RedisGenClient {
    // NX,XX
    //NX-key不存在则保存，XX-key存在则保存
    private static final String STNX= "NX";

    //EX,PX
    //EX-秒，PX-毫秒
    private static final String SET_EXPIRE_TIME = "PX";

    private RedisTemplate redisTemplate;
    private StringRedisTemplate stringRedisTemplate;
    // lua脚本删除
    private static final String COMPARE_AND_DELETE = "if redis.call('get',KEYS[1]) == ARGV[1]\n then\n     return redis.call('del',KEYS[1])\n else\n     return 0\n end";


    public RedisGenClient(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = new StringRedisTemplate();
        this.stringRedisTemplate.setConnectionFactory(redisTemplate.getConnectionFactory());
        this.stringRedisTemplate.afterPropertiesSet();
    }

    public RedisGenClient(StringRedisTemplate redisTemplate){
        this.stringRedisTemplate = redisTemplate;
    }


    public RedisLock addRedisLock(String lockKey,String requestId,long expireTime){
        String status = stringRedisTemplate.execute(
                new RedisCallback<String>() {
                    @Override
                    public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                        Object nativeConnection = redisConnection.getNativeConnection();
                        String status = null;
                        //集群 redis
                        if(nativeConnection instanceof JedisCluster){
                            System.out.println("JedisCluster set key:"+lockKey);
                            status = ((JedisCluster)nativeConnection).set(lockKey,lockKey,STNX,SET_EXPIRE_TIME,expireTime);
                        }
                        //单实例 redis
                        if(nativeConnection instanceof Jedis){
                            System.out.println("JedisCluster set key:"+lockKey);
                            status = ((Jedis)nativeConnection).set(lockKey,lockKey,STNX,SET_EXPIRE_TIME,expireTime);
                        }
                        return status;
                    }
                }
        );
        System.out.println("get controller status:"+status);
        if("OK".equals(status)){
            return new RedisLock() {

                @Override
                public boolean unlock() {
                    List<String> keyList = new Vector<>(1);

                    keyList.add(lockKey);
                    //stringRedisTemplate.delete(lockKey);
                    boolean flag = stringRedisTemplate.execute(new RedisCallback<Boolean>() {

                        @Override
                        public Boolean doInRedis(RedisConnection unlockRedisConnection) throws DataAccessException {
                            Long result = 0L;
                            System.out.println("unlock with key:"+lockKey);
                            Object unlockConnection = unlockRedisConnection.getNativeConnection();
                            if(unlockConnection instanceof JedisCluster){
                                System.out.println("JedisCluster unlock;");
                                result = (Long)((JedisCluster)unlockConnection).eval(COMPARE_AND_DELETE,keyList,keyList);
                            }
                            if(unlockConnection instanceof Jedis){
                                System.out.println("Jedis Client unlock;");
                                result = (Long)((Jedis)unlockConnection).eval(COMPARE_AND_DELETE,keyList,keyList);
                            }
                            return result==1L;
                        }
                    });
                    return flag;
                }
            };
        }else{
            return null;
        }
    }

}
