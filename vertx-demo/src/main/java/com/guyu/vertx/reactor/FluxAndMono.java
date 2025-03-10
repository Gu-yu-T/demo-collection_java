package com.guyu.vertx.reactor;

import java.util.Arrays;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxAndMono {

    public static Flux<String> getFlux1(){
        return Flux.just("apple","orange","banana");
    }

    public static Flux<String> getFlux2(){
        List<String> list =Arrays.asList("apple","orange","banana");
        return Flux.fromIterable(list);
    }

    public static Flux<Integer> getFlux3(){
        return Flux.range(5,3);
    }

    public static Mono<String> getMono1(){
        return Mono.just("hello");
    }



}
