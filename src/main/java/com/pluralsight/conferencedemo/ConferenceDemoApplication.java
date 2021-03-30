package com.pluralsight.conferencedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ConferenceDemoApplication extends SpringBootServletInitializer {
	/* extends SpringBootServletInitializer to pkg as a War file instead of as Jar */

	public static void main(String[] args) {
		//Spring Boot adds this App Class and main method automatically
		SpringApplication.run(ConferenceDemoApplication.class, args);
	}
}
