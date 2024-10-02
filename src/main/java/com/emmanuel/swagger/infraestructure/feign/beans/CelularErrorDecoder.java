package com.emmanuel.swagger.infraestructure.feign.beans;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CelularErrorDecoder implements ErrorDecoder{

	@Override
	public Exception decode(String methodKey, Response response) {	
		switch(response.status()) {
			case 400: return new RuntimeException("You haven't defined correctly the request.");
			case 404: return new RuntimeException("The service you have defined was not found.");
			case 500: return new RuntimeException("The service is not currently aviable.");
			default: return new RuntimeException("Something was wrong.");
		}
	}

}
