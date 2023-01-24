package com.moorabi.reelsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
public class NotAllowedException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public NotAllowedException(String message) {
		super(message);
	}
}
