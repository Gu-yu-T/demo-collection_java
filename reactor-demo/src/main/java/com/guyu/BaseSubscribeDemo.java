package com.guyu;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class BaseSubscribeDemo {
    public static void main(String[] args) {
        Flux<Integer> flux = Flux.range(1, 4);
        flux.doOnRequest(r -> System.out.println("第" + r + "个请求"))
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        System.out.println("SimpleSubscribe is sub!");
                        request(1);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println(value);
                        request(1);
                    }
                });


    }

}



