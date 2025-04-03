package com.prueba.TarjetasRegalo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TarjetasRegaloApplication {

	public static void main(String[] args) {
		SpringApplication.run(TarjetasRegaloApplication.class, args);
	}
}