package com.example.EmailMarketingSAAS.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@AllArgsConstructor
public class RedisService {

    private final RedisTemplate<String,Object> redisTemplate;

    public void SetValue(String key, Object value){
        redisTemplate.opsForValue().set(key,value);
    }
    public Object getValue(String key){
        return redisTemplate.opsForValue().get(key);
    }

    // Save data with expiry
    public void setValue(String key, Object value, long ttSecond){
        redisTemplate.opsForValue().set(key,value,Duration.ofSeconds(ttSecond));
    }

    public void deleteKey(String key){
        redisTemplate.delete(key);
    }

    public boolean existKey(String key){
        return redisTemplate.hasKey(key);
    }

}
