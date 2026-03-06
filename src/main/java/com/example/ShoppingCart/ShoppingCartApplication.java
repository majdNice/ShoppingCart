package com.example.ShoppingCart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class ShoppingCartApplication {
	private static final Logger logger = Logger.getLogger(ShoppingCartApplication.class.getName());

	public static void main(String[] args) {
		logger.info("Starting ShoppingCart application...");
		SpringApplication.run(ShoppingCartApplication.class, args);
	}
}