package com.emmanuel.swagger.infraestructure.feign.beans;

import com.emmanuel.swagger.infraestructure.feign.clients.ICelularClient;
import com.emmanuel.swagger.infraestructure.feign.dtoresponse.Celular;

public class CelularFallback implements ICelularClient{

	@Override
	public Celular getCelularFromFeignClient() {
		throw new RuntimeException("Something was wrong."); 
	}

}
