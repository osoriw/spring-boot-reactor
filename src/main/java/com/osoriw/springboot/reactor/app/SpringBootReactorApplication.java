package com.osoriw.springboot.reactor.app;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Ejemplo 1: creando un flujo reactivo
		System.out.println("Creando un flujo reactivo:");
		Flux<String> names1 = Flux.just("Andrés", "Rubén", "María", "Roberto", "Diego")
				.doOnNext(name -> System.out.println(name));

		names1.subscribe();
		System.out.println("\n");

		// Ejemplo 2: imprimiendo logs en el método subscriber
		System.out.println("Imprimiendo logs en el método subscriber:");
		Flux<String> names2 = Flux.just("Andrés", "Rubén", "María", "Roberto", "Diego")
				.doOnNext(name -> System.out.println(name));

		names2.subscribe(name -> log.info(name));
		System.out.println("\n");

		// Ejemplo 3: agregando control de excepciones
		System.out.println("Agregando control de excepciones:");
		Flux<String> names3 = Flux.just("Andrés", "Rubén", "", "María", "Roberto", "Diego").doOnNext(name -> {
			if (name.isEmpty()) {
				throw new RuntimeErrorException(null, "Names can't be empty.");
			}

			System.out.println(name);
		});

		names3.subscribe(name -> log.info(name), name -> log.error(name.getMessage()));
		System.out.println("\n");

		// Ejemplo 4: el evento onComplete
		System.out.println("El evento onComplete:");
		Flux<String> names4 = Flux.just("Andrés", "Rubén", "Julio", "María", "Roberto", "Diego").doOnNext(name -> {
			if (name.isEmpty()) {
				throw new RuntimeErrorException(null, "Names can't be empty.");
			}

			System.out.println(name);
		});

		names4.subscribe(name -> log.info(name), name -> log.error(name.getMessage()), new Runnable() {

			@Override
			public void run() {
				System.out.println("Flujo completado!!");
			}
		});
		System.out.println("\n");

		// Ejemplo 4: transformando los nombres a mayúsculas, después del método doOnNext:
		System.out.println("Transformando los nombres a mayúsculas, después del método doOnNext:");
		Flux<String> names5 = Flux.just("Andrés", "Rubén", "Julio", "María", "Roberto", "Diego").doOnNext(name -> {
			if (name.isEmpty()) {
				throw new RuntimeErrorException(null, "Names can't be empty.");
			}

			System.out.println(name);
		}).map(name -> name.toUpperCase());

		names5.subscribe(name -> log.info(name), name -> log.error(name.getMessage()), new Runnable() {

			@Override
			public void run() {
				System.out.println("Flujo completado!!");
			}
		});
		System.out.println("\n");

	}

}
