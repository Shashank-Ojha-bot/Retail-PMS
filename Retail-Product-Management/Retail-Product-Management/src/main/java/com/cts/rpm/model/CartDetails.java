package com.cts.rpm.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartDetails {

	private Integer cartId;
	private Integer productId;
	private Integer zipcode;
	private String customerName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime deliveryDate;
	private Integer vendorId;
	private Product product;
	private Vendor vendor;
}
