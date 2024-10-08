package com.emmanuel.swagger.infraestructure.serviceimpl;
import java.util.List;

import org.springframework.stereotype.Service;
import com.emmanuel.swagger.infraestructure.feign.clients.ICelularClient;
import com.emmanuel.swagger.infraestructure.feign.dtoresponse.Celular;
import com.emmanuel.swagger.infraestructure.service.ICellphoneService;
import com.emmanuel.swagger.infraestructure.util.exceptions.CelularNotFoundException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class CellphoneServiceImpl implements ICellphoneService{

	private final ICelularClient iCelularClient;

	
    public CellphoneServiceImpl(ICelularClient iCelularClient) {
        this.iCelularClient = iCelularClient;
    }
    
	@Override
	@CircuitBreaker(name="circuitBreakerBean", fallbackMethod="fallBackCelulars")
	public List<Celular> getAllPhones() {
		List<Celular> my_cellphones = iCelularClient.getCelularesFromFeignCliente();
		System.out.println(my_cellphones);
		return my_cellphones;
	}

    
	@Override
	@CircuitBreaker(name="cellphone", fallbackMethod="fallBackCelular")
	public Celular getCellPhone() {
		Celular my_cellphone = iCelularClient.getCelularFromFeignClient();
		System.out.println(my_cellphone);
		return my_cellphone;
	}

	public Celular fallBackCelular(Exception e) {
		System.out.println("Ha ocurrido un error");
		throw new CelularNotFoundException("Ha ocurrido un error");
	}
	
	public List<Celular> fallBackCelulars(Exception e) {
		System.out.println("Ha ocurrido un error");
		throw new CelularNotFoundException("Ha ocurrido un error");
	}
	
}
