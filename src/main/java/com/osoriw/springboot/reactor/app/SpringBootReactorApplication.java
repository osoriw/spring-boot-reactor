package com.osoriw.springboot.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(SpringBootApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Ejemplo 1: creando un flujo de datos tipo Flux e imprimiendo cada valor en el método doOnNext:
		Flux<String> names = Flux.just("Andrés", "Juan", "María", "Pedro", "Julian").doOnNext(name -> {
			System.out.println(name);
		});
		
		names.subscribe();
		
		// Ejemplo 2: manejando la excepción de nombre vacío:

		Flux<String> names = Flux.just("Andrés", "Juan", "María", "Pedro", "Julian").doOnNext(name -> {
			System.out.println(name);
		});
		
		names.subscribe();

		
		
		/*Flux<String> names = Flux.just("Andrés", "Juan", "María", "Pedro", "Julian").map(name -> name.toUpperCase())
				.doOnNext(e -> {
					if (e.isEmpty()) {
						throw new RuntimeException("Los Nombres no pueden ser vacíos");
					}
					System.out.println();
				});

		names.subscribe(e -> logger.info(e), error -> logger.error(error.getMessage()), new Runnable() {

			@Override
			public void run() {
				logger.info("Ha finalizado la ejecución del observable con éxito");
			}
		});*/

	}

}
