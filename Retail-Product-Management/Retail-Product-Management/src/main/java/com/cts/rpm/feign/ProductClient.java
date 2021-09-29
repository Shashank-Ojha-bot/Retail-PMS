package com.cts.rpm.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cts.rpm.model.Product;

@FeignClient(name = "Product-Microservice", url = "${product.url}")
@RequestMapping("${product.apiversion}")
public interface ProductClient {
	
	@GetMapping("/getAllProdcuts")
	public List<Product> getAllProducts(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader);
	
	@GetMapping("/searchProductById/{id}")
	public Product getProductById(@PathVariable Integer id,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader);
	
	@GetMapping("/searchProductName/{name}")
	public List<Product> getProductByName(@PathVariable String name,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader);
	
	@PostMapping("/addProductRating/{id}/{rating}")
	public Product addProductRating(@PathVariable Integer id,
			@PathVariable float rating,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader);
}
