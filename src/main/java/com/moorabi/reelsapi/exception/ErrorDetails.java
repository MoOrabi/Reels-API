package com.moorabi.reelsapi.exception;

import java.time.LocalDate;

public class ErrorDetails {
	/**
	 * 
	 */
	private LocalDate timestamp;
	private String code;
	private String message;
	private String details;
	
	public ErrorDetails(Errors errors,String details) {
		this.timestamp = LocalDate.now();
		this.code = errors.getCode();
		this.message = errors.getMessage();
		this.details=details;
	}


	public String getDetails() {
		return details;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}