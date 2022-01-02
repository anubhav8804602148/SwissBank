package com.cs.swiss;

import org.springframework.boot.SpringApplication;  
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class SwissBankApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SwissBankApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SwissBankApplication.class, args);
	}
}
