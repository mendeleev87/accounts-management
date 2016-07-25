package com.westernacher.account.dto;

import org.springframework.http.HttpStatus;

public class ErrorMessageDTO {
	
	private HttpStatus status;
	
	private String message;
	
	private String detailedMessage;

	public String getMessage() {
		return message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetailedMessage() {
		return detailedMessage;
	}

	public void setDetailedMessage(String detailedMessage) {
		this.detailedMessage = detailedMessage;
	}

	public ErrorMessageDTO(HttpStatus status, String message, String detailedMessage) {
		super();
		this.status = status;
		this.message = message;
		this.detailedMessage = detailedMessage;
	}

	public ErrorMessageDTO() {
		super();
	}

}
