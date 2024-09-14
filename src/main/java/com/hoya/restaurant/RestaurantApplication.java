package com.hoya.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Restaurant API", version = "1.0", description = "이 문서는 Restaurant API 입니다."))
public class RestaurantApplication {

	public static void main(String[] args) {

		SpringApplication.run(RestaurantApplication.class, args);
	}

}
