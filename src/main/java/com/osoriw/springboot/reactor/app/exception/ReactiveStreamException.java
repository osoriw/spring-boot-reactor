package com.osoriw.springboot.reactor.app.exception;

import reactor.core.publisher.Flux;

public class ReactiveStreamException {

    public Flux<String> handlingAnFluxException() {
        return Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Ocurred")))
                .concatWith(Flux.just("D"))
                .log();
    }
}
