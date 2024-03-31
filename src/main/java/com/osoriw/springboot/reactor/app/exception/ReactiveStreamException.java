package com.osoriw.springboot.reactor.app.exception;

import reactor.core.publisher.Flux;

public class ReactiveStreamException {

    public Flux<String> throwingAnException() {
        return Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Ocurred")))
                .concatWith(Flux.just("D"))
                .log();
    }

    public Flux<String> catchingAnExceptionWithOnErrorReturn() {
        return Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Ocurred")))
                .onErrorReturn("D")
                .log();
    }
}
