package com.emmanuel.swagger.api.dtoresponse;

import org.springframework.http.HttpStatus;

public class ErrorDto {

	private String msg;
	private HttpStatus status;
	private int statuscode;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public int getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}
}
