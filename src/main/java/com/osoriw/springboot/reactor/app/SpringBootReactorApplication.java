package com.osoriw.springboot.reactor.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.osoriw.springboot.reactor.app.models.Usuario;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		creatingAReactiveStream(); 
		
		printingLogsInSubscribeMethod();
		
		addingExceptionaManagement();
		
	    onCompleteEvent();
		
		mapOperator();
		
		flatMapOperator(); 

		filterMapOperator();
		
		inmutableObservables();
		
		creatingAReactiveFlowFromIterable();
	}
	
	private void creatingAReactiveStream() {
		System.out.println("EJEMPLO 1: Creando un flujo reactivo:");
		Flux<String> nombres = Flux.just("Andrés", "Rubén", "María", "Roberto", "Diego")
				.doOnNext(name -> System.out.println(name));

		nombres.subscribe();
		System.out.println("\n");
	}
	
	private void printingLogsInSubscribeMethod() {
		System.out.println("EJEMPLO 2: Imprimiendo logs en el método subscriber:");
		Flux<String> nombres = Flux.just("Andrés", "Rubén", "María", "Roberto", "Diego")
				.doOnNext(name -> System.out.println(name));

		nombres.subscribe(name -> log.info(name));
		System.out.println("\n");
	}
	
	private void addingExceptionaManagement() {
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
	
	private void onCompleteEvent() {
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

	private void mapOperator() {
		System.out.println("EJEMPLO 5: Transformando los nombres a objetos tipo Usuario con el operador map:");
		Flux<Usuario> users1 = Flux.just("Andrés", "Rubén", "Julio", "María", "Roberto", "Diego")
				.map(name -> new Usuario(name.toUpperCase(), null)).doOnNext(user -> {
				});

		users1.subscribe(user -> log.info(user.toString()));
	}
	
	private void flatMapOperator() {
		System.out.println("EJEMPLO 6: usando el operador flatmap:");
		List<String> nombresList = new ArrayList<>();
		nombresList.add("Andrés Guzman");
		nombresList.add("Rubén Fulano");
		nombresList.add("Julio Sultano");
		nombresList.add("María Mengano");
		nombresList.add("Roberto Muchilanga");
		nombresList.add("Diego Burundanga");
		nombresList.add("Bruce Lee");
		nombresList.add("Bruce Willis");

		Flux.fromIterable(nombresList)
				.flatMap(nombre -> Mono.just(new Usuario(nombre.split(" ")[0], nombre.split(" ")[1])))
				.subscribe(user -> log.info(user.toString()));

		System.out.println("\n");

	}
	
	private void filterMapOperator() {
		System.out.println("EJEMPLO 7: Agregando operador filter:");
		Flux.just("Andrés Guzman", "Rubén Fulano", "Julio Sultano", "María Mengano", "Roberto Muchilanga",
				"Diego Burundanga", "Bruce Lee", "Bruce Willis").filter(nombre -> nombre.split(" ")[0].equals("bruce"))
				.subscribe(nombre -> log.info(nombre.toString()));
	}
	
	private void inmutableObservables() {
		System.out.println("EJEMPLO 8: Validando inmutabilidad del flujo:");
		Flux<String> nombres = Flux.just("Andrés Guzman", "Rubén Fulano", "Julio Sultano", "María Mengano",
				"Roberto Muchilanga", "Diego Burundanga", "Bruce Lee", "Bruce Willis");

		Flux<Usuario> usuarios = nombres
				.map(name -> new Usuario(name.split(" ")[0].toUpperCase(), name.split(" ")[1].toUpperCase()));

		System.out.println("Flujo inicial es inmutable, sus datos no cambian:");
		nombres.subscribe(user -> log.info(user.toString()));
		System.out.println("\n");

		System.out.println("Flujo modificado es diferente del flujo inicial:");
		usuarios.subscribe(user -> log.info(user.toString()));
		System.out.println("\n");
	}
		
	
	private void creatingAReactiveFlowFromIterable() {
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

		nombresFlx.subscribe(user -> log.info(user.toString()));
		System.out.println("\n");
	}
	
	
}
