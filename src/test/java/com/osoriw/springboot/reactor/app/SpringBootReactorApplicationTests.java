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
    void handlingAnFluxExceptionTest() {
        //given

        //when
        ReactiveStreamException main = new ReactiveStreamException();
        var value = main.handlingnFluxException();

        //then
        StepVerifier.create(value)
                .expectNext("A", "B", "C")
                .expectError(RuntimeException.class)
                .verify();
    }


}
