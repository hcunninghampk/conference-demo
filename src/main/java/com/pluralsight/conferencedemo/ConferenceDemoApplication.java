package com.pluralsight.conferencedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConferenceDemoApplication {

	public static void main(String[] args) {
		//Spring Boot adds this App Class and main method auto
		SpringApplication.run(ConferenceDemoApplication.class, args);
	}
}