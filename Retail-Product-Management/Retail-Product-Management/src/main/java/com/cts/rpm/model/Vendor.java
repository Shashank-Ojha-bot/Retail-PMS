package com.cts.rpm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {
	
	private Integer vendorId;
	private String vendorName;
	private Float deliveryCharge;
	private Float vendorRating;

}
