package com.emmanuel.swagger.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import com.emmanuel.swagger.api.dtorequest.ProductDtoRequest;
import com.emmanuel.swagger.infraestructure.serviceimpl.ProductServiceImpl;

import jakarta.annotation.PostConstruct;

@Configuration
@EnableCaching	//By this annotation we are going to enable the cache in our spring boot application.
public class CacheConfiguration {

	@Autowired
	ProductServiceImpl productServiceImpl;
	
	@Autowired
	CacheManager cacheManager;
	
	@PostConstruct		//We are going to use this method for pre loading the cache in complitation time.
	void preLoadCache() {
		Cache my_cache = cacheManager.getCache("product");
		List<ProductDtoRequest> my_products = productServiceImpl.getAllProducts();
		
		//For each product that we have in the database, we are going to create one key and its value in the cache.
		my_products.stream().forEach(el -> {
			my_cache.put(el.getId(), el);
		});
	}
	
}
