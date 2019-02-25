package com.clare.redislock;

/**
 * @author liuxu
 * @ProjectName ems-payment-service
 * @PackageName cn.com.crc.redis
 * @ClassName RedisLock
 * @date 2018/10/22 15:05
 * @since JDK 1.8
 */
public interface RedisLock extends AutoCloseable  {

  /**
   * 释放分布式锁
   */
  public boolean unlock();
}
