package com.cts.vendor.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cts.vendor.dto.Product;

@FeignClient( name = "Product-Service", url = "${product.url}" )
@RequestMapping("${product.apiversion}")
public interface ProductClient {

	@GetMapping("/searchProductById/{id}")
	public Product getProductById(@PathVariable Integer id,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader);
}
