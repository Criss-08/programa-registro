package com.cristian.programaregistro;

import com.cristian.programaregistro.entity.EstadoTrabajo;
import com.cristian.programaregistro.repository.EstadoTrabajoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
