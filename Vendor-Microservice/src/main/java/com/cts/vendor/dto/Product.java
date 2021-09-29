package com.cts.vendor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
	
	private Integer id;
	private Float price;
	private String name;
	private String description;
	private String image_name;
	private Float rating;
}
