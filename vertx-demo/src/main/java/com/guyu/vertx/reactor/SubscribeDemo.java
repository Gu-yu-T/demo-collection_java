package com.guyu.vertx.reactor;

import reactor.core.publisher.Flux;

public class SubscribeDemo {
    public static void main(String[] args) {
            sub2();
    }

    public static void sub1(){
        Flux<Integer> flux = FluxAndMono.getFlux3();
        flux.subscribe();
    }

    public static void sub2(){
        Flux<Integer> flux = Flux.range(4,2)
                        .map(i->{
                            if (i<7){
                                return i;
                            }else {
                                throw new RuntimeException("该数大于7");
                            }
                        });
        flux.subscribe(i-> System.out.println(i),
                e-> System.out.println("Error:"+e),
                ()-> System.out.println("Done!"),
                subscription -> subscription.request(1));
    }

}
