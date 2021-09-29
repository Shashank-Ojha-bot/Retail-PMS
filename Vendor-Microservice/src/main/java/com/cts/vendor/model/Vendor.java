package com.cts.vendor.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Vendor {

	@Id
	private Integer vendorId;
	private String vendorName;
	private Float deliveryCharge;
	private Float vendorRating;
	@OneToMany(mappedBy = "vendor")
	@JsonIgnore
	private List<VendorStock> stocks=new ArrayList<VendorStock>();
	
	
	public Vendor(Integer vendorId, String vendorName, Float deliveryCharge, Float vendorRating) {
		super();
		this.vendorId = vendorId;
		this.vendorName = vendorName;
		this.deliveryCharge = deliveryCharge;
		this.vendorRating = vendorRating;
	}
	
}
