package com.emmanuel.swagger.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.emmanuel.swagger.api.dtoresponse.ErrorDto;
import com.emmanuel.swagger.infraestructure.util.exceptions.CelularNotFoundException;

@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler(CelularNotFoundException.class)
	ResponseEntity<ErrorDto> celularException(CelularNotFoundException e){
		ErrorDto error = new ErrorDto();
		error.setMsg(e.getMessage());
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setStatuscode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
}
