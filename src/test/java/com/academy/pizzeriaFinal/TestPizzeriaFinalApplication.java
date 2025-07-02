package com.academy.pizzeriaFinal;

import org.springframework.boot.SpringApplication;

public class TestPizzeriaFinalApplication {

	public static void main(String[] args) {
		SpringApplication.from(PizzeriaFinalApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
