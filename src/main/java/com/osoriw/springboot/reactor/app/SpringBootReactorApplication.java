package com.osoriw.springboot.reactor.app;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.osoriw.springboot.reactor.app.models.Usuario;

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
		System.out.println("EJEMPLO 1: Creando un flujo reactivo:");
		Flux<String> names1 = Flux.just("Andrés", "Rubén", "María", "Roberto", "Diego")
				.doOnNext(name -> System.out.println(name));

		names1.subscribe();
		System.out.println("\n");

		// Ejemplo 2: imprimiendo logs en el método subscriber
		System.out.println("EJEMPLO 2: Imprimiendo logs en el método subscriber:");
		Flux<String> names2 = Flux.just("Andrés", "Rubén", "María", "Roberto", "Diego")
				.doOnNext(name -> System.out.println(name));

		names2.subscribe(name -> log.info(name));
		System.out.println("\n");

		// Ejemplo 3: agregando control de excepciones
		System.out.println("EJEMPLO 3: Agregando control de excepciones:");
		Flux<String> names3 = Flux.just("Andrés", "Rubén", "", "María", "Roberto", "Diego").doOnNext(name -> {
			if (name.isEmpty()) {
				throw new RuntimeErrorException(null, "Names can't be empty.");
			}

			System.out.println(name);
		});

		names3.subscribe(name -> log.info(name), name -> log.error(name.getMessage()));
		System.out.println("\n");

		// Ejemplo 4: el evento onComplete
		System.out.println("EJEMPLO 4: El evento onComplete:");
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

		// Ejemplo 5: transformando los nombres a mayúsculas, después del método
		// doOnNext:
		System.out.println("EJEMPLO 5: Transformando los nombres a mayúsculas, después del método doOnNext:");
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

		// Ejemplo 6: transformando los nombres a objetos tipo Usuario:
		System.out.println("EJEMPLO 6: Transformando los nombres a objetos tipo Usuario:");
		Flux<Usuario> users1 = Flux.just("Andrés", "Rubén", "Julio", "María", "Roberto", "Diego")
				.map(name -> new Usuario(name.toUpperCase(), null)).doOnNext(user -> {
					if (user == null || user.getName().isEmpty()) {
						throw new RuntimeErrorException(null, "Names can't be empty.");
					}

					System.out.println(user.getName());
				});

		users1.subscribe(user -> log.info(user.toString()), name -> log.error(name.getMessage()), new Runnable() {

			@Override
			public void run() {
				System.out.println("Flujo completado!!");
			}
		});
		System.out.println("\n");
		
		// Ejemplo 7: agregando operador filter:
		System.out.println("EJEMPLO 7: Agregando operador filter:");
		Flux<Usuario> users2 = Flux
				.just("Andrés Guzman", "Rubén Fulano", "Julio Sultano", "María Mengano", "Roberto Muchilanga", "Diego Burundanga", "Bruce Lee", "Bruce Willis")
				.map(name -> new Usuario(name.split(" ")[0].toUpperCase(), name.split(" ")[1].toUpperCase()))
				.filter(user -> user.getName().toLowerCase().equals("bruce")).doOnNext(user -> {
					if (user == null || user.getName().isEmpty()) {
						throw new RuntimeErrorException(null, "Names can't be empty.");
					}

					System.out.println(user.getName().toLowerCase().concat(" ").concat(user.getLastName().toLowerCase()));
				});

		users2.subscribe(user -> log.info(user.toString()), name -> log.error(name.getMessage()), new Runnable() {

			@Override
			public void run() {
				System.out.println("Flujo completado!!");
			}
		});
		System.out.println("\n");
		
		// Ejemplo 8: validando inmutabilidad del flujo:
		System.out.println("EJEMPLO 8: Validando inmutabilidad del flujo:");
		Flux<String> nombres = Flux.just("Andrés Guzman", "Rubén Fulano", "Julio Sultano", "María Mengano", "Roberto Muchilanga", "Diego Burundanga", "Bruce Lee", "Bruce Willis");
				
		Flux<Usuario> usuarios = nombres.map(name -> new Usuario(name.split(" ")[0].toUpperCase(), name.split(" ")[1].toUpperCase()))
				.filter(user -> user.getName().toLowerCase().equals("bruce")).doOnNext(user -> {
					if (user == null || user.getName().isEmpty()) {
						throw new RuntimeErrorException(null, "Names can't be empty.");
					}

					System.out.println(user.getName().toLowerCase().concat(" ").concat(user.getLastName().toLowerCase()));
				});

		System.out.println("Flujo inicial es inmutable, sus datos no cambian:");
		nombres.subscribe(user -> log.info(user.toString()), name -> log.error(name.getMessage()), new Runnable() {

			@Override
			public void run() {
				System.out.println("Flujo completado!!");
			}
		});
		System.out.println("\n");
		
		System.out.println("Flujo modificado es diferente del flujo inicial:");
		usuarios.subscribe(user -> log.info(user.toString()), name -> log.error(name.getMessage()), new Runnable() {

			@Override
			public void run() {
				System.out.println("Flujo completado!!");
			}
		});
		System.out.println("\n");


	}

}
