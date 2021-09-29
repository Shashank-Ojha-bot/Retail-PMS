package com.cts.vendor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.vendor.exception.AuthorizationException;
import com.cts.vendor.feign.AuthorisingClient;
import com.cts.vendor.feign.ProductClient;
import com.cts.vendor.dto.Product;
import com.cts.vendor.model.Vendor;
import com.cts.vendor.service.VendorService;
import com.cts.vendor.service.VendorStockService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class VendorController {

	@Autowired
	private VendorStockService vendorStockService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private AuthorisingClient authorisingClient;
	
	@Autowired
	private ProductClient productClient;
	
	@GetMapping("/getVendorDetails/{productId}")
	@ApiOperation(notes="Get list of vendors who sale product based n product Id",value="get vendors list")
	public List<Vendor> getVendorDetails(@ApiParam(name = "productId", value = "id of the product") @PathVariable Integer productId,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader){
		
		log.info("Uri /getVendorDetails/{productId} entered");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			Product product=productClient.getProductById(productId, requestTokenHeader);
			return vendorStockService.getAllVendorListWithProductId(product.getId());
		} else {
			log.error("Authorization error in /getVendorDetails/{productId} uri");
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@GetMapping("/getVendorDetailsById/{vendorId}")
	@ApiOperation(notes="Get vendor details based on id",value="Vendor Details")
	public Vendor getVendorDetailsById(@ApiParam(name = "vendorId", value = "id of the vendor") @PathVariable Integer vendorId,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader){
		
		log.info("Uri /getVendorDetailsById/{vendorId} entered");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			
			return vendorService.getVendorById(vendorId);
		} else {
			log.error("Authorization error in /getVendorDetailsById/{vendorId} uri");
			throw new AuthorizationException("Not allowed");
		}
	}

}
