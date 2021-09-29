package com.cts.product.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.product.exception.InvalidRatingException;
import com.cts.product.exception.ProductNotFoundException;
import com.cts.product.model.Product;
import com.cts.product.repository.ProductRepository;
import com.cts.product.service.ProductService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

	
	@Autowired
	private ProductRepository productRepository;
	
	/*
	 * To get Product Details By Id 
	 */
	@Override
	public Product getProductById(Integer id) {
		// TODO Auto-generated method stub
		
		Product product=productRepository.findById(id)
				.orElseThrow(()-> new ProductNotFoundException("Product with id "+id+" not found"));
		log.info("Product Details ("+product+")");
		return product;
	}

	/*
	 * To get Product Details that contains "name"(passed argument) in product Name
	 */
	@Override
	public List<Product> getProductByName(String name){

		List<Product> list=productRepository.findByNameContainsIgnoreCase(name);
		if(list.isEmpty()) {
			throw new ProductNotFoundException("Product with name "+name+" not Found");
		}
		else {
			log.info("Product Details ("+name+")");
			return list;
		}
	
	}

	/*
	 * To add rating to product with id
	 */
	@Override
	public Product addProductRating(Integer productId, Float rating) {
		// TODO Auto-generated method stub
		
		Product product=productRepository.findById(productId)
				.orElseThrow(()-> new ProductNotFoundException("Product with id "+productId+" not found"));
		if(rating<=0 || rating >5) {
			
			throw new InvalidRatingException(rating+" is Invalid rating. Rating should be greater than 0 and less than than 5");
		}
		else {
			product.setRating(rating);
			productRepository.save(product);
			log.info("Added Rating to the Product");
			return product;
		}
		
	}

	/*
	 * To get Details of all the products 
	 */
	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		
		List<Product> listProducts=productRepository.findAll();
		log.info("Details of all the products fetched");
		return listProducts;
	}

}
