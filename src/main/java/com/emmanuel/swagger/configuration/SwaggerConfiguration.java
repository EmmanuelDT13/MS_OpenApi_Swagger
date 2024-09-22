package com.emmanuel.swagger.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfiguration {

	
	@Bean
	OpenAPI getConfigOpenApi() {
		
		return new OpenAPI().info(new Info().description("This is one API that Emmanuel Duenas has developed for practicing"
				+ " his skills in Spring OpenApi/Swagger")
				.title("MS_Swagger")
				.version("1.0.0")
				.contact(new Contact()
						.name("Emmanuel Duenas")
						.email("theemanuel211@gmail.com")
						.url("https://www.linkedin.com/in/torresemma")));
	}
}
