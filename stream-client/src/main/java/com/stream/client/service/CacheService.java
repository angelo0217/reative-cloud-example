package com.stream.client.service;

import com.stream.client.config.redis.cache.CacheKey;
import com.stream.common.model.RedisVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@CacheConfig(cacheNames = CacheKey.TEST_CACHE)
public class CacheService {

    @CachePut(key = "{#name, 'name'}" , unless="#result == null")
    public RedisVo saveRedisVo(String name, RedisVo redisVo) {
        return redisVo;
    }

    @Cacheable(key = "{#name, 'name'}" , unless="#result == null")
    public RedisVo getRedisVo(String name) {
        RedisVo redisVo = new RedisVo();
        redisVo.setName("Dean4");
        redisVo.setAge(new BigDecimal(1));
        log.info("=============== start cache");

        return redisVo;
    }


    @CacheEvict(key = "{#chatId, 'name'}")
    public void remove(String name) {
        log.debug("remove name {}", name);
    }


    @CacheEvict(value = CacheKey.TEST_CACHE, allEntries = true)
    public void clearCache() {
        log.info("Clear all user cache.");
    }
}
