package org.rch.jarvisapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class JarvisAppApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(JarvisAppApplication.class, args);
	}

}
