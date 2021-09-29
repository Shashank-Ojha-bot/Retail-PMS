package com.cts.rpm.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class CustomerWishListDetails {

	private Integer productId;
	private String customerName;
	private Integer quantity;
	private Product product;
	private LocalDateTime dateAddedToWishlist;
}
