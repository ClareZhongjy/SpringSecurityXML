package com.clare.redislock.controller;

import com.clare.redislock.RedisGenClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("RedisLock")
@Api(value = "redis锁controller")
public class LockController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @PostMapping("/doRedisLock")
    @ApiOperation(value = "/doRedisLock")
    public boolean doRedisLock(@ApiParam(value = "key") @RequestParam("key") String key){
        try {
            RedisGenClient redisGenClient = new RedisGenClient(stringRedisTemplate);

            redisGenClient.addRedisLock(key, "", 300);

            return true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
