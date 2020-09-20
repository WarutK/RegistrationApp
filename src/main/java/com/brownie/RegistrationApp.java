package com.brownie;

import java.util.Arrays;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class RegistrationApp {
	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationApp.class);

	public static void main(String[] args) {
		
		SpringApplication.run(RegistrationApp.class, args);
		logger.debug("--Application Started--");
		
	}
}
