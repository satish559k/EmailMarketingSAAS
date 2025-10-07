package com.example.EmailMarketingSAAS.service;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Map;

@Service
@AllArgsConstructor
public class RedisService {

    private final RedisTemplate<String,Object> redisTemplate;

    public void SetValue(String key, Object value){
        redisTemplate.opsForValue().setIfAbsent(key,value);
    }
    public Object getValue(String key){
        return redisTemplate.opsForValue().get(key);
    }

    // Save data with expiry
    public void setValue(String key, Object value, long ttSecond){
        redisTemplate.opsForValue().setIfAbsent(key,value,Duration.ofSeconds(ttSecond));
    }
    // Save data with expiry midnight
    public void setValueWithTTL(String key, Object value){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endOfDay = now.toLocalDate().atTime(23, 59, 59);
        // Calculate seconds until end of day
        long secondsUntilEndOfDay = Math.max(Duration.between(now, endOfDay).getSeconds(), 1);

        redisTemplate.opsForValue().setIfAbsent(key, value, Duration.ofSeconds(secondsUntilEndOfDay));
    }

    public void deleteKey(String key){
        redisTemplate.delete(key);
    }

    public boolean existKey(String key){
        return redisTemplate.hasKey(key);
    }

    //##### using Hash ################
    public void setValueHashWithTTL(String hashKey,String key, Object value){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endOfDay = now.toLocalDate().atTime(23, 59, 59);
        // Calculate seconds until end of day
        long secondsUntilEndOfDay = Math.max(Duration.between(now, endOfDay).getSeconds(), 1);

        redisTemplate.opsForHash().put(hashKey,key,value);
        redisTemplate.expire(hashKey,Duration.ofSeconds(secondsUntilEndOfDay));
    }

    public Map<Object, Object> getValueHash(String hashKey){
        return redisTemplate.opsForHash().entries(hashKey);
    }

    public void deleteHash(String hashKey, String key){
        redisTemplate.opsForHash().delete(hashKey,key);
    }

}
