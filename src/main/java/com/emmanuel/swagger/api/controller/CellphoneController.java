package com.emmanuel.swagger.api.controller;
import com.emmanuel.swagger.infraestructure.feign.dtoresponse.Celular;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.emmanuel.swagger.infraestructure.service.ICellphoneService;

@RestController
@RequestMapping(path="/cellphones")
public class CellphoneController {

	private final ICellphoneService iCellphoneService;
	
	public CellphoneController(ICellphoneService iCellphoneService) {
		this.iCellphoneService = iCellphoneService;
		
	}
	
	@GetMapping(path="/getAll")
	public ResponseEntity<Celular> getAllCellphones(){
		Celular my_cellphone = iCellphoneService.getCellPhone();
		return new ResponseEntity<>(my_cellphone, HttpStatus.OK);
	}
	
	@GetMapping(path="/getOne")
	public ResponseEntity<Celular> getOneCellphone(){
		Celular my_cellphone = iCellphoneService.getCellPhone();
		return new ResponseEntity<>(my_cellphone, HttpStatus.OK);
	}
}
