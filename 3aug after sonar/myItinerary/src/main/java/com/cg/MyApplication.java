package com.cg;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MyApplication extends SpringBootServletInitializer {
	private static final Logger log = LogManager.getLogger("MyApplication.class");

	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
		log.info("My Itinerary Application Started.");
	}
}