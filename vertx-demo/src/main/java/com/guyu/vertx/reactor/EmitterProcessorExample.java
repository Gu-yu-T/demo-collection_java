package com.guyu.vertx.reactor;

import java.util.function.Function;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class EmitterProcessorExample {
    public static void main(String[] args) {
        //创建一个 EmitterProcessor 实例
        EmitterProcessor<Integer> processor = EmitterProcessor.create(false);

        //使用 map(Function.identity()), reactor中的操作符，使溜中的数据保持不变
//        Flux<Integer> flux = processor.map(Function.identity());
//
//        flux.subscribe(item -> System.out.println("Received: "+item));
        processor.subscribe(item -> System.out.println("Received: "+item));

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
