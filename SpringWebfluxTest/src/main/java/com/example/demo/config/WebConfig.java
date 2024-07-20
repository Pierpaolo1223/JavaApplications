package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebConfig implements WebFluxConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // Applica a tutti gli endpoint
				.allowedOrigins("http://localhost:4200") // Sostituisci con i tuoi domini consentiti
				.allowedMethods("GET", "POST", "PUT", "DELETE") // Metodi HTTP consentiti
				.allowedHeaders("*") // Header HTTP consentiti
				.allowCredentials(true) // Supporto per le credenziali
				.maxAge(3600); // Tempo di cache della preflight response
	}
}
