package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		//System.out.println("***** EJECUTANDO APLICACION ******");
		//System.out.print("Current directory: " + System.getProperty("user.dir"));
		SpringApplication.run(BackendApplication.class, args);
	}

}
