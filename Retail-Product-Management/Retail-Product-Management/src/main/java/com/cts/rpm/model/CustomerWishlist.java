package com.cts.rpm.model;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerWishlist {
	
	int id;
	private String customerName;
	private Integer productId;
	private Integer quantity;
	private LocalDateTime dateAddedToWishlist;
	
}
