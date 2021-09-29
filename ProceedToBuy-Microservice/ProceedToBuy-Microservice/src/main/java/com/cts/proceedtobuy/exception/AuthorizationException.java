package com.cts.proceedtobuy.exception;

@SuppressWarnings("serial")
public class AuthorizationException extends RuntimeException  {
	
	public AuthorizationException(String message) {
		super(message);
	}
}
