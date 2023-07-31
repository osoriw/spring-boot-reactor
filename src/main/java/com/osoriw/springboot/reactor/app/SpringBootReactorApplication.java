package com.osoriw.springboot.reactor.app;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.osoriw.springboot.reactor.app.models.Comentarios;
import com.osoriw.springboot.reactor.app.models.Usuario;
import com.osoriw.springboot.reactor.app.models.UsuarioComentarios;

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

		/*creatingAReactiveStream();

		printingLogsInSubscribeMethod();

		addingExceptionaManagement();

		onCompleteEvent();

		mapOperator();

		flatMapOperator();

		filterMapOperator();

		inmutableObservables();

		creatingAReactiveFlowFromIterable();

		listToString();

		fluxToMono();

		usuarioComentariosFlatmapExample();
		
		usuarioComentariosZipWithExampleWay1();
		
		usuarioComentariosZipWithExampleWay2();

		zipWithRangeExample();*/
		
		intervalExample();
		
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

		System.out.println("\n");
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
				"Diego Burundanga", "Bruce Lee", "Bruce Willis")
				.filter(nombre -> nombre.split(" ")[0].toLowerCase().equals("bruce"))
				.subscribe(nombre -> log.info(nombre.toString()));

		System.out.println("\n");
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

	private void listToString() {
		System.out.println("EJEMPLO 10: Convirtiendo una lista de usuarios a un flujo de Strings con los operadores map y flatmap:");
		List<Usuario> usuariosList = new ArrayList<>();
		usuariosList.add(new Usuario("Andrés", "Guzman"));
		usuariosList.add(new Usuario("Rubén", "Fulano"));
		usuariosList.add(new Usuario("Julio", "Sultano"));
		usuariosList.add(new Usuario("María", "Mengano"));
		usuariosList.add(new Usuario("María", "Muchilanga"));
		usuariosList.add(new Usuario("Diego", "Burundanga"));
		usuariosList.add(new Usuario("Bruce", "Lee"));
		usuariosList.add(new Usuario("Bruce", "Willis"));

		Flux.fromIterable(usuariosList).map(usuario -> usuario.getName().concat(" ").concat(usuario.getLastName()))
				.flatMap(nombres -> Mono.just(
						nombres.split(" ")[0].toUpperCase().concat(" ").concat(nombres.split(" ")[1].toUpperCase())))
				.subscribe(nombre -> log.info(nombre));

		System.out.println("\n");
	}

	private void fluxToMono() {
		System.out.println("EJEMPLO 11: Convirtiendo un flujo observable Flux a Mono, con el método collectList(), Flux<Usuarios> -> Mono<List<Usuario>>:");
		List<Usuario> usuariosList = new ArrayList<>();
		usuariosList.add(new Usuario("Andrés", "Guzman"));
		usuariosList.add(new Usuario("Rubén", "Fulano"));
		usuariosList.add(new Usuario("Julio", "Sultano"));
		usuariosList.add(new Usuario("María", "Mengano"));
		usuariosList.add(new Usuario("María", "Muchilanga"));
		usuariosList.add(new Usuario("Diego", "Burundanga"));
		usuariosList.add(new Usuario("Bruce", "Lee"));
		usuariosList.add(new Usuario("Bruce", "Willis"));

		// collectList transforma el Flux de usuarios en un Mono de una lista de usuarios Flux<Usuarios> -> Mono<List<Usuario>>
		Mono<List<Usuario>> usuarioFlx = Flux.fromIterable(usuariosList).collectList();
		usuarioFlx.subscribe(listaUsuarios -> log.info(listaUsuarios.toString()));

		System.out.println("Recorrer el Mono<List<Usuario>> con un forEach:");
		usuarioFlx.subscribe(listaUsuarios -> listaUsuarios.forEach(System.out::println));

		System.out.println("\n");
	}

	private void usuarioComentariosFlatmapExample() {
		System.out.println("EJEMPLO 12: Combinando 2 flujos con el operador flatMap:");
		// crear un nuevo flujo usuarioMono, usando fromCallable
		Mono<Usuario> usuarioMono = Mono.fromCallable(() -> new Usuario("Alejandro", "Rodriguez"));

		// crear un nuevo flujo comentariosUsuarioMono, usando fromCallable
		Mono<Comentarios> comentariosUsuarioMono = Mono.fromCallable(() -> {
			Comentarios comentarios = new Comentarios();
			comentarios.setComentario("Comentario 1");
			comentarios.setComentario("Comentario 2");
			comentarios.setComentario("Comentario 3");
			comentarios.setComentario("Comentario 4");

			return comentarios;

		});

		usuarioMono.flatMap(u -> comentariosUsuarioMono.map(c -> new UsuarioComentarios(u, c)))
				.subscribe(uc -> log.info(uc.toString()));

		System.out.println("\n");
	}
	
	private void usuarioComentariosZipWithExampleWay1() {
		System.out.println("EJEMPLO 13: Combinando 2 flujos con el operador zipWith forma 1:");
		// crear un nuevo flujo usuarioMono, usando fromCallable
		Mono<Usuario> usuarioMono = Mono.fromCallable(() -> new Usuario("Alejandro", "Rodriguez"));

		// crear un nuevo flujo comentariosUsuarioMono, usando fromCallable
		Mono<Comentarios> comentariosUsuarioMono = Mono.fromCallable(() -> {
			Comentarios comentarios = new Comentarios();
			comentarios.setComentario("Comentario 1");
			comentarios.setComentario("Comentario 2");
			comentarios.setComentario("Comentario 3");
			comentarios.setComentario("Comentario 4");

			return comentarios;

		});

		Mono<UsuarioComentarios> usuarioComentariosMono = usuarioMono.zipWith(comentariosUsuarioMono,
				(usuario, comentario) -> new UsuarioComentarios(usuario, comentario));

		usuarioComentariosMono.subscribe(uc -> log.info(uc.toString().toString()));

		System.out.println("\n");
	}
	
	private void usuarioComentariosZipWithExampleWay2() {
		System.out.println("EJEMPLO 13: Combinando 2 flujos con el operador zipWith forma 2:");
		// crear un nuevo flujo usuarioMono, usando fromCallable
		Mono<Usuario> usuarioMono = Mono.fromCallable(() -> new Usuario("Alejandro", "Rodriguez"));

		// crear un nuevo flujo comentariosUsuarioMono, usando fromCallable
		Mono<Comentarios> comentariosUsuarioMono = Mono.fromCallable(() -> {
			Comentarios comentarios = new Comentarios();
			comentarios.setComentario("Comentario 1");
			comentarios.setComentario("Comentario 2");
			comentarios.setComentario("Comentario 3");
			comentarios.setComentario("Comentario 4");

			return comentarios;

		});

		Mono<UsuarioComentarios> usuarioComentariosMono = usuarioMono.zipWith(comentariosUsuarioMono).map(tuple -> {
			Usuario usuario = tuple.getT1();
			Comentarios comentarios = tuple.getT2();
			return new UsuarioComentarios(usuario, comentarios);
		});

		usuarioComentariosMono.subscribe(uc -> log.info(uc.toString()));
		
		System.out.println("\n");
	}
	
	private void zipWithRangeExample() {
		System.out.println("EJEMPLO 14: otro ejemplo de combinación  de 2 flujos con el operador zipWith:");
		Flux<Integer> range = Flux.range(1, 10);

		range.map(i -> (i * 2)).zipWith(range, (firtsFlow, secondFlow) -> String.format("Primer Flux: %d, Segundo Flux: %d ", firtsFlow, secondFlow))
				.subscribe(texto -> log.info(texto));

		System.out.println("\n");
	}

	private void intervalExample() throws InterruptedException {
		Flux<Integer> rango = Flux.range(1, 12);
		Flux<Long> retraso = Flux.interval(Duration.ofSeconds(1));

		System.out.println("Iniciando el bloqueo del hilo principal...");
		rango.zipWith(retraso, (ra, re) -> ra).doOnNext(i -> log.info(i.toString()))
				/**
				 * Por defecto, no es posible ver la impresión de los valores del rango, debido
				 * a la naturaleza no bloqueante del flujo reactivo, por lo cual el proceso de
				 * imprimir cada uno de valores del rango con un delay de un 1 segundo, se
				 * ejecuta en segundo plano y como la ejecución de hilo principal se demora mu
				 * poco tiempo, la ejecución de este, terminará mucho antes de que empiece
				 * siquiera a emitir el primer valor del rango. Por esta razón y por efectos de
				 * poder mostrar la impresión de cada de los valores, se debe bloquear el hilo
				 * principal, una opción para hacer esto es invocando el método blockLast()
				 * (ojo!, en escenario real, no se debe bloquear un flujo reactivo pues esto no
				 * tiene sentido, ya que el propósito de la programación reactiva es
				 * precisamente que se puedan correr procesos en hilos independientes no
				 * bloqueantes).
				 */
				.blockLast(); // se bloquea el hilo principal
		System.out.println("Finalizando el bloqueo del hilo principal...");

		System.out.println("\n");
	}

}
