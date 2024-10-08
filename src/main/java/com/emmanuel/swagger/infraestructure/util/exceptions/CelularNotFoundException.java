package com.emmanuel.swagger.infraestructure.util.exceptions;

public class CelularNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

    public CelularNotFoundException(String msg) {
        super(msg + ": No celular was found.");
    }
	
}
