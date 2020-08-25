package com.stream.client.controller;

import com.stream.client.service.CacheService;
import com.stream.common.model.RedisVo;
import com.stream.common.model.UserVo;
import com.stream.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;

@RestController
public class RedisController {


    @Autowired
    private ReactiveRedisTemplate reactiveRedisTemplate;

    @Autowired
    private RedisTemplate<String, RedisVo> redisTemplate;

    @Autowired
    private CacheService cacheService;

    @GetMapping("/redis1")
    public void test(){
        ReactiveHashOperations<String, String, String> hashOps = reactiveRedisTemplate.opsForHash();
        Mono mono1 = hashOps.put("apple", "x", "6000");
        mono1.subscribe(System.out::println);
        Mono mono2 = hashOps.put("apple", "xr", "5000");
        mono2.subscribe(System.out::println);
        Mono mono3 = hashOps.put("apple", "xs max", "8000");
        mono3.subscribe(System.out::println);

    }

    @GetMapping("/deleteVal")
    public Flux deleteVal() {
        Mono a = reactiveRedisTemplate.delete("a");
        Mono b = reactiveRedisTemplate.delete("b");
        Mono c = reactiveRedisTemplate.delete("c");
        a.subscribe(System.out::println);//这里需要消费才行。否则无法真正操作。
        b.subscribe(System.out::println);
        c.subscribe(System.out::println);

        return Flux.just(a, c);
    }

    @GetMapping("testRedis1")
    public void findCityById() {
        Mono mono1 = reactiveRedisTemplate.opsForValue().set("c", "vvvv");
        mono1.subscribe(System.out::println);

        UserVo userVo = new UserVo();
        userVo.setAge(20);
        userVo.setName("Dean");
        Mono mono2 = reactiveRedisTemplate.opsForValue().set("a", JsonUtil.objectToJson(userVo));
        mono2.subscribe(System.out::println);


        RedisVo redisVo = new RedisVo();
        redisVo.setAge(new BigDecimal(30));
        redisVo.setName("Dean2");
        //这里可以直接设置对象
        Mono mono3 = reactiveRedisTemplate.opsForValue().set("b", redisVo);
        mono3.subscribe(System.out::println);
    }

    @GetMapping("/testRedis2")
    public Mono<String> testReactorRedis2() {
        Mono mono = reactiveRedisTemplate.opsForValue().get("a");
        return mono;
    }

    @GetMapping("/testRedis3")
    public Mono<RedisVo> testReactorRedis3() {
        return reactiveRedisTemplate.opsForValue().get("b");
    }

    @GetMapping("/testRedis4")
    public Flux<Object> testReactorRedis4() {
        Flux flux = Flux.just("a", "b", "c")
                .flatMap(s -> reactiveRedisTemplate.opsForValue().get(s));
        flux.subscribe(System.out::println);
        return flux;
    }


    @GetMapping("/testRedis5")
    public Map testReactorRedis5() {
        Mono monoA = reactiveRedisTemplate.opsForValue().get("a");
        Mono monoC = reactiveRedisTemplate.opsForValue().get("c");
        return Map.of("monoA", monoA, "monoC", monoC);
    }

    @GetMapping("/norRedis1")
    public void save() {
        RedisVo redisVo = new RedisVo();
        redisVo.setAge(new BigDecimal(33));
        redisVo.setName("dean3");
        redisTemplate.opsForValue().set("nor", redisVo);
    }

    @GetMapping("/norRedis2")
    public RedisVo get() {
        return redisTemplate.opsForValue().get("nor");
    }

    @GetMapping("/getCache")
    public RedisVo getCache(){
        return cacheService.getRedisVo("test");
    }
}
