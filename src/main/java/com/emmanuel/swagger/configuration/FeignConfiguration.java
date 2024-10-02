package com.emmanuel.swagger.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.emmanuel.swagger.infraestructure.feign.beans.CelularErrorDecoder;
import com.emmanuel.swagger.infraestructure.feign.beans.CelularFallback;
import com.emmanuel.swagger.infraestructure.feign.beans.CelularRequestInterceptor;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;

@Configuration
@EnableFeignClients(basePackages="com.emmanuel.swagger.infraestructure.feign.clients")
public class FeignConfiguration {

	@Bean
	ErrorDecoder sendErrorDecoder() {
		return new CelularErrorDecoder();
	}
	
	@Bean
	CelularFallback sendFallback() {
		return new CelularFallback();
	}
	
	@Bean
	RequestInterceptor sendRequestInterceptor() {
		return new CelularRequestInterceptor();
	}
	
}
