package com.emmanuel.swagger.infraestructure.serviceimpl;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.emmanuel.swagger.infraestructure.feign.clients.ICelularClient;
import com.emmanuel.swagger.infraestructure.feign.dtoresponse.Celular;
import com.emmanuel.swagger.infraestructure.service.ICellphoneService;
import com.emmanuel.swagger.infraestructure.util.exceptions.CelularNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class CellphoneServiceImpl implements ICellphoneService{

	private final ICelularClient iCelularClient;

	private final io.github.resilience4j.circuitbreaker.CircuitBreaker circuitBreaker;
	
    public CellphoneServiceImpl(ICelularClient iCelularClient, @Qualifier("circuitBreakerBean") io.github.resilience4j.circuitbreaker.CircuitBreaker circuitBreaker) {
        this.iCelularClient = iCelularClient;
        this.circuitBreaker = circuitBreaker;
    }
    
	@Override
	public List<Celular> getAllPhones() {
		
		try {
			circuitBreaker.executeSupplier(() ->{
				List<Celular> my_cellphones = iCelularClient.getCelularesFromFeignCliente();
				System.out.println(my_cellphones);
				return my_cellphones;
			});
			
		}catch(Exception e) {
			return fallBackCelulars(e);
		}
		return null;
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
