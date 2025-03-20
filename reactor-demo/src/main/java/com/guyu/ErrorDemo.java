package com.guyu;

import java.io.IOException;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;

public class ErrorDemo {
    public static void main(String[] args) {
        Flux.range(1, 4)
                .map(i -> {
                    try {
                        if (i < 3) {
                            return i;
                        } else {
                            throw new NoSuchFieldException();
                        }
                    } catch (Exception e) {
                        throw Exceptions.propagate(e);
                    }
                }).subscribe(v -> System.out.println("value:" + v),
                        e -> {
                            if (Exceptions.unwrap(e) instanceof IOException) {
                                System.out.println("ioexception happen");
                            }else {
                                System.out.println("other exception happen");
                            }
                        });

    }
}
