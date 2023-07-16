package com.moorabi.reelsapi.exception;

import java.time.LocalDate;

public class ErrorDetails extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private LocalDate timestamp;
	private String code;
	private String errorTypeMessage;
	private String message;
	
	public ErrorDetails(Errors errors,String details) {
		this.timestamp = LocalDate.now();
		this.code = errors.getCode();
		this.errorTypeMessage = errors.getMessage();
		this.message=details;
	}


	public String getMessage() {
		return message;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public String getCode() {
		return code;
	}

	public String getErrorTypeMessage() {
		return errorTypeMessage;
	}
}