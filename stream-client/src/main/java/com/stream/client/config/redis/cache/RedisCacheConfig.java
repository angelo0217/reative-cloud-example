package com.stream.client.config.redis.cache;

import com.stream.common.util.JacksonSerializerUtil;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@EnableCaching
@Configuration
public class RedisCacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {

        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(factory),
                this.getRedisCacheConfigurationWithTtl(60 * 10), // 默認策略，未配置的 key 會使用這個
                this.getRedisCacheConfigurationMap() // 指定 key 策略
        );
    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        //SsoCache和BasicDataCache進行過期的時間配置
        for (RedisCacheKey redisCacheKey : RedisCacheKey.values()) {
            redisCacheConfigurationMap.put(redisCacheKey.getName(), this.getRedisCacheConfigurationWithTtl(redisCacheKey.getKeepSecond()));
        }

        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(
                RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(JacksonSerializerUtil.jackson2JsonRedisSerializer()));
        if (seconds > 0) {
            redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofSeconds(seconds));
        }
        return redisCacheConfiguration;
    }
}
