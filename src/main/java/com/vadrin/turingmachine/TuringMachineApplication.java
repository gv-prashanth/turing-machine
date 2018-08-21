package com.vadrin.turingmachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TuringMachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(TuringMachineApplication.class, args);
	}
}
