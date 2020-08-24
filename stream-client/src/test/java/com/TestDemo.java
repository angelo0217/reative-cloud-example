package com;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
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
}
