package com.osoriw.springboot.reactor.app;

import java.util.ArrayList;
import java.util.List;

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

		creandoFlujoReactivo(); // creando un flujo reactivo
		imprimiendoLogsEnMetodoSubscriber(); // imprimiendo logs en el método subscriber
		agregandoControlDeExcepciones();// agregando control de excepciones
		eventoOnComplete();// el evento onComplete
		
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
		
		// Ejemplo 9: Creando un flujo reactivo a partir de un iterable (List, Array, Stream, Set, etc):
		System.out.println("EJEMPLO 9: Creando un flujo reactivo a partir de un iterable (List, Array, Stream, Set, etc):");
		List<String> nombresList = new ArrayList<>();
		nombresList.add("Andrés Guzman");
		nombresList.add("Rubén Fulano");
		nombresList.add("Julio Sultano");
		nombresList.add("María Mengano");
		nombresList.add("Roberto Muchilanga");
		nombresList.add("Diego Burundanga");
		nombresList.add("Bruce Lee");
		nombresList.add("Bruce Willis");

		Flux<String> nombresFlx = Flux.fromIterable(nombresList);
		nombresFlx.map(name -> new Usuario(name.split(" ")[0].toUpperCase(), name.split(" ")[1].toUpperCase()))
				.filter(user -> user.getName().toLowerCase().equals("bruce")).doOnNext(user -> {
					if (user == null || user.getName().isEmpty()) {
						throw new RuntimeErrorException(null, "Names can't be empty.");
					}

					System.out.println(user.getName().toLowerCase().concat(" ").concat(user.getLastName().toLowerCase()));
				});

		nombresFlx.subscribe(user -> log.info(user.toString()), name -> log.error(name.getMessage()), new Runnable() {

			@Override
			public void run() {
				System.out.println("Flujo completado!!");
			}
		});
		System.out.println("\n");

		
	
	}
	
	private void creandoFlujoReactivo() {
		System.out.println("EJEMPLO 1: Creando un flujo reactivo:");
		Flux<String> nombres = Flux.just("Andrés", "Rubén", "María", "Roberto", "Diego")
				.doOnNext(name -> System.out.println(name));

		nombres.subscribe();
		System.out.println("\n");
	}
	
	private void imprimiendoLogsEnMetodoSubscriber() {
		System.out.println("EJEMPLO 2: Imprimiendo logs en el método subscriber:");
		Flux<String> nombres = Flux.just("Andrés", "Rubén", "María", "Roberto", "Diego")
				.doOnNext(name -> System.out.println(name));

		nombres.subscribe(name -> log.info(name));
		System.out.println("\n");
	}
	
	private void agregandoControlDeExcepciones() {
		System.out.println("EJEMPLO 3: Agregando control de excepciones:");
		Flux<String> nombres = Flux.just("Andrés", "Rubén", "", "María", "Roberto", "Diego").doOnNext(name -> {
			if (name.isEmpty()) {
				throw new RuntimeErrorException(null, "Names can't be empty.");
			}
			System.out.println(name);
		});

		nombres.subscribe(name -> log.info(name), name -> log.error(name.getMessage()));
		System.out.println("\n");
	}
	
	private void eventoOnComplete() {
		System.out.println("EJEMPLO 4: El evento onComplete:");
		Flux<String> nombres = Flux.just("Andrés", "Rubén", "Julio", "María", "Roberto", "Diego").doOnNext(name -> {
			if (name.isEmpty()) {
				throw new RuntimeErrorException(null, "Names can't be empty.");
			}

			System.out.println(name);
		});

		nombres.subscribe(name -> log.info(name), name -> log.error(name.getMessage()), new Runnable() {

			@Override
			public void run() {
				System.out.println("Flujo completado!!");
			}
		});
		System.out.println("\n");
	}

}
