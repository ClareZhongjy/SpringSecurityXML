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
import java.util.concurrent.TimeUnit;

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


    public boolean addRedisLock(String lockKey,String requestId,long expireTime){
        boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey,lockKey,expireTime, TimeUnit.MILLISECONDS);
        return result;
    }

}
