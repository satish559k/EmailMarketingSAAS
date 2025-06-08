package com.example.EmailMarketingSAAS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmailMarketingSaasApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailMarketingSaasApplication.class, args);
	}

}
