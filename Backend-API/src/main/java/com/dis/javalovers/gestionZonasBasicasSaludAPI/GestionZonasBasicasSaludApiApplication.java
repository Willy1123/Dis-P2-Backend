package com.dis.javalovers.gestionZonasBasicasSaludAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestionZonasBasicasSaludApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionZonasBasicasSaludApiApplication.class, args);
		/* URL Para comporbar el API usando Swagger:
		 * localhost:8081/swagger-ui/index.html
		 * Cambiar el 8081 por el puerto en el que se ejecute la API
		 */
	}

}
