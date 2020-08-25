package com;

import com.stream.client.ClientApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClientApplication.class)
public class TestRedis {

    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;

    @Test
    public void test(){
        ReactiveHashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        Mono mono1 = hashOps.put("apple", "x", "6000");
        mono1.subscribe(System.out::println);
        Mono mono2 = hashOps.put("apple", "xr", "5000");
        mono2.subscribe(System.out::println);
        Mono mono3 = hashOps.put("apple", "xs max", "8000");
        mono3.subscribe(System.out::println);

    }
}
