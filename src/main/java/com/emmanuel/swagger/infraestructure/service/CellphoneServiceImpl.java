package com.emmanuel.swagger.infraestructure.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.emmanuel.swagger.infraestructure.feign.clients.ICelularClient;
import com.emmanuel.swagger.infraestructure.feign.dtoresponse.Celular;

@Service
public class CellphoneServiceImpl implements ICellphoneService{

	private final ICelularClient iCelularClient;

	
    public CellphoneServiceImpl(ICelularClient iCelularClient) {
        this.iCelularClient = iCelularClient;
    }
	
	@Override
	public Celular getCellPhone() {
		Celular my_cellphone = iCelularClient.getCelularFromFeignClient();
		System.out.println(my_cellphone);
		return my_cellphone;
	}

}
