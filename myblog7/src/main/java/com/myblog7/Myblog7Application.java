package com.myblog7;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.myblog7")
public class Myblog7Application {

	public static void main(String[] args) {
		SpringApplication.run(Myblog7Application.class, args);
	}

	@Bean
	public ModelMapper modelmMapper() {

		return new ModelMapper();
	}

}
