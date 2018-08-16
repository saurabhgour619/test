package com.cg.error;

import org.springframework.stereotype.Component;

@Component
public class BookingErrorResponse {

	private int status;
	private String message;

	public BookingErrorResponse() {
	}

	public BookingErrorResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}