package com.cts.rpm.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
	private Integer cartId;
	private Integer productId;
	private Integer zipcode;
	private String customerName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime deliveryDate;
	private Integer vendorId;

}
