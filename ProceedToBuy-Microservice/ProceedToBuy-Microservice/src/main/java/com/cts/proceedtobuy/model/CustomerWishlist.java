package com.cts.proceedtobuy.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerWishlist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	private String customerName;
	private Integer productId;
	private Integer quantity;
	private LocalDateTime dateAddedToWishlist;
	
}
