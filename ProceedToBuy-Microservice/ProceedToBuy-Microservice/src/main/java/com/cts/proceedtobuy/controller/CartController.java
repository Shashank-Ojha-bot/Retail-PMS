package com.cts.proceedtobuy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.proceedtobuy.exception.AuthorizationException;
import com.cts.proceedtobuy.feign.AuthorisingClient;
import com.cts.proceedtobuy.feign.VendorClient;
import com.cts.proceedtobuy.model.Cart;
import com.cts.proceedtobuy.model.CustomerWishlist;
import com.cts.proceedtobuy.dto.Vendor;
import com.cts.proceedtobuy.service.CartService;
import com.cts.proceedtobuy.service.CustomerWishListService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CustomerWishListService customerWishListService;
	
	@Autowired
	private AuthorisingClient authorisingClient;
	
	@Autowired
	private VendorClient vendorClient;
	
	@PostMapping("/addProductToCart")
	@ApiOperation(notes = "Adds the product to the Cart", value="Add new Product to Cart")
	public Cart addProductToCart(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@RequestBody Cart cart) {
		
		log.info("Uri /addProductToCart entered");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			
			//fetching the details of vendors that sells product with productId from Vendor Micro-Service.
			List<Vendor> listVendors=vendorClient.getVendorDetails(cart.getProductId(), requestTokenHeader);
			log.info("fetched the details of vendors that sells product with productId from Vendor Micro-Service");
			Cart addedCart=cartService.addProductToCart(cart,listVendors);
			return addedCart;
		}else {
			
			log.error("Authorization erro in /addProductToCart uri");
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@PostMapping("/addProductToWishlist")
	@ApiOperation(notes = "Adds the product to the Wish List and returns Status", value="Add Product to Wish List")
	public ResponseEntity<String> addProductToWishlist(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@RequestBody CustomerWishlist customerWishlist){
		
		log.info("Uri /addProductToWishlist entered");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			customerWishListService.addToCustomerWishlist(customerWishlist);
			return new ResponseEntity<>("Product added to WishList",HttpStatus.OK);
		}else {
			
			log.error("Authorization erro in /addProductToWishlist uri");
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@GetMapping("/getCarts/{customerName}")
	@ApiOperation(notes = "Returns all the carts for a Customer", value="Get Carts")
	public List<Cart> getCarts(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@ApiParam(name="customerName",value = "Name of the Customer") @PathVariable String customerName){
		
		log.info("Uri /getCarts/{customerName} entered");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return cartService.getCarts(customerName);
		}else {
			
			log.error("Authorization erro in /getCarts/{customerName} uri");
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@GetMapping("/getCustomerWishList/{customerName}")
	@ApiOperation(notes = "Returns all the carts for a Customer", value="Get Carts")
	public List<CustomerWishlist> getCustomerWishList(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@ApiParam(name="customerName",value = "Name of the Customer") @PathVariable String customerName){
		
		log.info("Uri /getCustomerWishList/{customerName} entered");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return customerWishListService.getCustomerWishList(customerName);
		}else {
			
			log.error("Authorization erro in /getCustomerWishList/{customerName} uri");
			throw new AuthorizationException("Not allowed");
		}
	}
}
