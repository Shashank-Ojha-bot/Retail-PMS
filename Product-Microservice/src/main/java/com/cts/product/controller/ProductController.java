package com.cts.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.product.exception.AuthorizationException;
import com.cts.product.feign.AuthorisingClient;
import com.cts.product.model.Product;
import com.cts.product.service.ProductService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class ProductController {

	@Autowired
	private AuthorisingClient authorisingClient;
	
	@Autowired
	private ProductService productService;

	@GetMapping("/getAllProdcuts")
	@ApiOperation(notes="Return List of all products",value="Get all products")
	public List<Product> getAllProducts(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) {
		
		log.info("Uri /getAllProducts called");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return productService.getAllProducts();
		} else {
			log.error("Authorization error occured in /getAllProducts uri call");
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@GetMapping("/searchProductById/{id}")
	@ApiOperation(notes="Return product details based on Id",value="Find product by Id")
	public Product getProductById(@ApiParam(name = "id", value = "Id of the product") @PathVariable Integer id,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) {
		
		log.info("Uri /searchProductById/{id} called");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return productService.getProductById(id);
		} else {
			log.error("Authorization error occured in /searchProductById/{id} uri call");
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@GetMapping("/searchProductName/{name}")
	@ApiOperation(notes="Return product details based on Name",value="Find product by Name")
	public List<Product> getProductByName(@ApiParam(name = "name", value = "Name of the product") @PathVariable String name,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) {
		
		log.info("Uri /searchProductName/{name} called");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return productService.getProductByName(name);
		} else {
			log.error("Authorization error occured in /searchProductName/{name} uri call");
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@PostMapping("/addProductRating/{id}/{rating}")
	@ApiOperation(notes="Add rating for product based on productId",value="Add rating of product")
	public Product addProductRating(@ApiParam(name = "id", value = "id of the product") @PathVariable Integer id,
			@ApiParam(name = "rating", value = "rating of the product") @PathVariable Float rating,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) {
		
		log.info("Uri /addProductRating/{id}/{rating} called");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return productService.addProductRating(id, rating);
		} else {
			log.error("Authorization error occured in /addProductRating/{id}/{rating} uri call");
			throw new AuthorizationException("Not allowed");
		}
	}
	
}
