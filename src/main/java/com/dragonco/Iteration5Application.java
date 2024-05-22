package com.dragonco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Iteration5Application {

	public static void main(String[] args) {
		SpringApplication.run(Iteration5Application.class, args);
	}

}
