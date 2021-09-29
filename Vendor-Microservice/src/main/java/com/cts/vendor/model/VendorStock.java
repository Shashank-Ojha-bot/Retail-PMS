package com.cts.vendor.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class VendorStock {

	@Id
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="vendor_id")
	private Vendor vendor;
	
	private int productId;
	
	private int stockInHand;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime replinshmentDate;
}
