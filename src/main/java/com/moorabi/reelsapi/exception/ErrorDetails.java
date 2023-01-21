package com.moorabi.reelsapi.exception;

import java.time.LocalDate;
import java.util.Date;

public class ErrorDetails extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDate timestamp;
	private String message;
	private String details;

	public ErrorDetails(LocalDate localDate, String message, String details) {
		super();
		this.timestamp = localDate;
		this.message = message;
		this.details = details;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
}