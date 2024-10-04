package com.emmanuel.swagger.infraestructure.service;

import java.util.List;

import com.emmanuel.swagger.infraestructure.feign.dtoresponse.Celular;

public interface ICellphoneService {

	public List<Celular> getAllPhones();
	
	public Celular getCellPhone();
	
}
