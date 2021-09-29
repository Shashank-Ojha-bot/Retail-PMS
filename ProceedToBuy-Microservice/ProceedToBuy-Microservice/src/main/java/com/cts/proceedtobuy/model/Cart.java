package com.cts.proceedtobuy.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cartId;
	private Integer productId;
	private Integer zipcode;
	private String customerName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime deliveryDate;
	private Integer vendorId;
	
}
