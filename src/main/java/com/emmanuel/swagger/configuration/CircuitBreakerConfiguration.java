package com.emmanuel.swagger.configuration;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Configuration
public class CircuitBreakerConfiguration {

	@Bean
	CircuitBreaker getCircuitBreakerBean() {
		
		//Este objeto nos permitirá definir y configurar nuestra instancia de CircuitBreaker.
		CircuitBreakerConfig circuitBreakerInstance = CircuitBreakerConfig.custom()
				.slidingWindowSize(10)		//El total de llamados que considerará para verificar las fallas.
				.slidingWindowType(SlidingWindowType.COUNT_BASED)	//Si considerará el número de llamadas fallidas
				.failureRateThreshold(50)	//Qué porcentaje de llamadas fallidas tomará para pasar a open.
				.minimumNumberOfCalls(5)	//A partir de cuántas llamadas habilitará el failureRateThreshold.
				.waitDurationInOpenState(Duration.ofSeconds(10))	//A los cuántos segundos pasará de open - half open.
				.automaticTransitionFromOpenToHalfOpenEnabled(true)	//¿Pasará automáticamente de open - halp open.
				.permittedNumberOfCallsInHalfOpenState(3)	//Cuántas llamadas fallidas permitirá half open para pasar a open.
				.ignoreExceptions(IllegalArgumentException.class)	//Qué excepciones no tomará en cuenta para determinar fallo.
				.writableStackTraceEnabled(false)	//¿Se incluirá toda la pila de llamadas fallidas?
				.slowCallDurationThreshold(Duration.ofSeconds(3)) //A los cuántos segundos de tardanza se considera fallo.
				.slowCallRateThreshold(50)	//Cuánto porcentaje de llamadas lentas de considerará fallo
				.build();
		//Este objeto registrará nuestra instancia en el contexto de la aplicación.
		CircuitBreakerRegistry register = CircuitBreakerRegistry.of(circuitBreakerInstance);
		return register.circuitBreaker("circuitBreakerBean");
	}
	
	
}
