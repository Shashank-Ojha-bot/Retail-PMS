package com.cts.proceedtobuy.dto;

import lombok.AllArgsConstructor;

import lombok.Getter;

@Getter
@AllArgsConstructor
public class Vendor {
	
	private Integer vendorId;
	private String vendorName;
	private Float deliveryCharge;
	private Float vendorRating;

}
