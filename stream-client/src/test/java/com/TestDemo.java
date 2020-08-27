package com;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDemo {
    @Test
    public void test1(){
        Flux.just(2,34,56,75,4,56).collectList()
                .subscribe(list -> list.forEach(value -> System.out.println(value+"  ")));

        List<String> strs = new ArrayList<>();
        strs.add("2");
        strs.add("21");
        strs.add("22");
        strs.add("23");
        Flux.just(strs).subscribe(list -> list.forEach(value -> System.out.println(value+"  ")));
    }

    @Test
    public void test2(){
        for(int i = 0 ; i< 10 ; i++){
            Flux.just(i).subscribe(value -> System.out.println(value+"  "));
        }
    }

    @Test
    public void test3(){
        Flux.combineLatest(
                Arrays::toString,
                Flux.interval(Duration.of(0, ChronoUnit.MILLIS),
                        Duration.of(100, ChronoUnit.MILLIS)).take(2),
                Flux.interval(Duration.of(50, ChronoUnit.MILLIS),
                        Duration.of(100, ChronoUnit.MILLIS)).take(2)
        ).toStream().forEach(System.out::println);
    }

    @Test
    public void test4(){
        Flux.mergeSequential(
                Flux.just(11,12,13,14,15),
                Flux.just(1,2,3,4,5))
                .toStream()
                .forEach(System.out::println);
//        System.out.println("---");
//        Flux.mergeSequential(Flux.interval(
//                Duration.of(0, ChronoUnit.MILLIS),
//                Duration.of(100, ChronoUnit.MILLIS)).take(2),
//                Flux.interval(
//                        Duration.of(50, ChronoUnit.MILLIS),
//                        Duration.of(100, ChronoUnit.MILLIS)).take(2))
//                .toStream()
//                .forEach(System.out::println);
    }

    @Test
    public void test5(){
        Flux<String> flux1 = Flux.just("a", "b", "c", "d", "e");
        Flux<String> flux2 = Flux.just("A", "B", "C", "D");
        Flux<String> flux3 = Flux.just("1", "2", "3", "4", "5");

        flux1.zipWith(flux2).subscribe(System.out::println);



    }
}

