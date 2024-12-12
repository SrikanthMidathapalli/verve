package com.verve.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean isUniqueRequest(Integer id) {
        return !redisTemplate.hasKey(String.valueOf(id));
    }

    public void addRequest(Integer id) {
        redisTemplate.opsForValue().set(String.valueOf(id), "true");
    }
    
    
}
