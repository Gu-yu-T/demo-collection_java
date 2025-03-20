package com.guyu;

import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Slf4j
public class PublishAndMap {
    public static void main(String[] args) {
        EmitterProcessor<Integer> processor = EmitterProcessor.create(false);
        FluxSink<Integer> sink = processor.sink();

        Flux<Integer> map = processor.map(Function.identity());

        for (int i = 0; i < 10; i++) {
            sink.next(i);
        }
        map.map(i->{
            log.info("i:"+i);
            return i;
        }).subscribe();

    }
}
