package com.osoriw.springboot.reactor.app.exception;

import reactor.core.publisher.Flux;

public class ReactiveStreamException {

    /**
     * Throws an exception when an error occurs
     */
    public Flux<String> throwingAnException() {
        return Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("An exception has occurred")))
                .concatWith(Flux.just("D"))
                .log();
    }

    /**
     * Return a default value when an exception occurs
     */
    public Flux<String> handlingAnExceptionWithOnErrorReturn() {
        return Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("An exception has occurred")))
                .onErrorReturn("D") //Note that this method does not have visibility of the exception. it just catches the exception and returns a default value when the exception occurs
                .log();
    }

}
