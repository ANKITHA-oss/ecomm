package com.mtd.ecom_server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	public ResourceNotFoundExceptoin(String msg) {super();}
}
