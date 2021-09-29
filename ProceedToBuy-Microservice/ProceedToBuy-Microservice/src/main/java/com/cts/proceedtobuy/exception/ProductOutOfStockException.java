package com.cts.proceedtobuy.exception;

@SuppressWarnings("serial")
public class ProductOutOfStockException extends RuntimeException  {
	
	public ProductOutOfStockException(String message) {
		super(message);
	}
}
