package com.cristian.programaregistro;
 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication // Configuración principal de Spring Boot
public class ProgramaregistroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgramaregistroApplication.class, args);
	}

/*	@Bean // Registra un objeto en el contenedor de Spring
	CommandLineRunner init(EstadoTrabajoRepository repository){
		return args -> {
			EstadoTrabajo estado = new EstadoTrabajo();
			estado.setNombre("Pendiente");

			repository.save(estado);

			System.out.println("Estado guardado correctamente");
		};
	}*/



}
