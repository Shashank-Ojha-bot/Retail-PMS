package com.cts.rpm.exception;

@SuppressWarnings("serial")
public class AuthorizationException extends RuntimeException  {
	
	public AuthorizationException(String message) {
		super(message);
	}
}
