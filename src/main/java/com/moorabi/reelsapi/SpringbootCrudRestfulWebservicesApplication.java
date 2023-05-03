package com.moorabi.reelsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import com.moorabi.reelsapi.controller.fcontrollers.FrontController;

@SpringBootApplication
public class SpringbootCrudRestfulWebservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCrudRestfulWebservicesApplication.class, args);
		
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
}

