package com.guyu.vertx.reactor;

import java.util.function.Function;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class EmitterProcessorExample {
    public static void main(String[] args) {
        method2();
    }

    public static void method2() {
        EmitterProcessor<Integer> processor = EmitterProcessor.create(false);

        Scheduler publish = Schedulers.newSingle("publish1");
        Scheduler sub1 = Schedulers.newParallel("sub1", 2);

        processor.map(i -> {
                    System.out.println("线程1：" + Thread.currentThread().getName());
                    return i;
                })
//                .publishOn(publish)
//                .map(i -> {
//                    System.out.println("线程2：" + Thread.currentThread().getName());
//                    return i;
//                })
                .parallel()
                .map(i -> {
                    System.out.println("线程3：" + Thread.currentThread().getName());
                    return i;
                })
                .runOn(sub1, 1)
                .map(i -> {
                    System.out.println("线程4：" + Thread.currentThread().getName());
                    return i;
                })
                .subscribe(v -> System.out.println("消息为5：" + v + "，线程：" + Thread.currentThread().getName()));

        FluxSink<Integer> sink = processor.sink();
        for (int i = 0; i < 3; i++) {
            sink.next(i);
        }
    }

    public static void method1() {
        //创建一个 EmitterProcessor 实例
        EmitterProcessor<Integer> processor = EmitterProcessor.create(false);

        //使用 map(Function.identity()), reactor中的操作符，使溜中的数据保持不变
//        Flux<Integer> flux = processor.map(Function.identity());
//
//        flux.subscribe(item -> System.out.println("Received: "+item));
        processor.subscribe(item -> System.out.println("Received: " + item));

        //向处理器发布数据
       /* processor.onNext(1);
        processor.onNext(2);
        processor.onNext(3);*/

        FluxSink<Integer> sink = processor.sink();
        sink.next(4);

        //完成流的发布
        processor.onComplete();
    }
}
