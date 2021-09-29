package com.cts.product.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductTest {

	@Test
	public void testProductSetters() {
		//Product product=new Product(10,50f,"product","description","image",4f);
		Product product=new Product();
		product.setId(10);
		product.setName("product");
		product.setPrice(50f);
		product.setDescription("description");
		product.setImageName("image");
		//product.setRating(4f);
		assertEquals(product.getId(),10);
		assertEquals(product.getImageName(),"image");
		assertEquals(product.getName(),"product");
		assertEquals(product.getPrice(),50f);
		assertEquals(product.getDescription(), "description");
	}
}
