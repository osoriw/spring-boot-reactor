package com.osoriw.springboot.reactor.app;

import com.osoriw.springboot.reactor.app.exception.ReactiveStreamException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
class SpringBootReactorApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void throwingAnExceptionTest() {
        //given

        //when
        ReactiveStreamException main = new ReactiveStreamException();
        var value = main.throwingAnException();

        //then
        StepVerifier.create(value)
                .expectNext("A", "B", "C")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void catchingAnExceptionWithOnErrorReturnTest() {
        //given

        //when
        ReactiveStreamException main = new ReactiveStreamException();
        var value = main.catchingAnExceptionWithOnErrorReturn();

        //then
        StepVerifier.create(value)
                .expectNext("A", "B", "C", "D")
                .verifyComplete();
    }


}
