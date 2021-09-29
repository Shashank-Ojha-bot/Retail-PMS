package com.cts.product.service;

import java.util.List;

//import java.util.Optional;

//import com.cts.product.exception.ProductNotFoundException;
import com.cts.product.model.Product;

public interface ProductService {

	public Product getProductById(Integer id);
	public List<Product> getProductByName(String name);
	public Product addProductRating(Integer productId,Float rating);
	public List<Product> getAllProducts();
	
}
