package com.cts.rpm.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cts.rpm.model.CustomerWishlist;
import com.cts.rpm.model.Cart;

@FeignClient(name = "ProceedToBuy-Microservice", url = "${cart.url}")
@RequestMapping("${cart.apiversion}")
public interface ProceedToBuyClient {
	
	@PostMapping("/addProductToCart")
	public Cart addProductToCart(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@RequestBody Cart cart);
	
	@PostMapping("/addProductToWishlist")
	public ResponseEntity<String> addProductToWishlist(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@RequestBody CustomerWishlist customerWishlist);
	
	@GetMapping("/getCarts/{customerName}")
	public List<Cart> getCarts(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@PathVariable String customerName);
	
	@GetMapping("/getCustomerWishList/{customerName}")
	public List<CustomerWishlist> getCustomerWishList(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			 @PathVariable String customerName);
}
