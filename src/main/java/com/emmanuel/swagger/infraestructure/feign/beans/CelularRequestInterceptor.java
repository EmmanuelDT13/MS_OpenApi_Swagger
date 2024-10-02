package com.emmanuel.swagger.infraestructure.feign.beans;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class CelularRequestInterceptor implements RequestInterceptor{
	@Override
	public void apply(RequestTemplate template) {
		System.out.println("Calling the apply method from RequestInterceptor...");
		template.header("Header1", "Header From RequestInterceptor");
	}
}
