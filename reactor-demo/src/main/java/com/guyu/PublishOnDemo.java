package com.guyu;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class PublishOnDemo {
    public static void main(String[] args) {
        Scheduler scheduler = Schedulers.newParallel("parallel-scheduler", 4);
        Flux<String> flux = Flux.range(1, 2)
                .map(i -> i + ":" + Thread.currentThread().getName())
                .publishOn(scheduler)
                .map(i -> "value" + i + Thread.currentThread().getName());
        new Thread(()->flux.subscribe(v-> System.out.println("value+_:"+v+":"+Thread.currentThread().getName()))).start();
    }
}
