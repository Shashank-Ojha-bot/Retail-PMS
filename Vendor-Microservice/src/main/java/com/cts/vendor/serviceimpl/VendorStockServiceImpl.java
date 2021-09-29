package com.cts.vendor.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.vendor.model.Vendor;
import com.cts.vendor.repository.VendorStockRepository;
import com.cts.vendor.service.VendorStockService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VendorStockServiceImpl implements VendorStockService{
	
	@Autowired
	private VendorStockRepository vendorStockRepository;

	/*
	 * Returns List of All the Vendors that sells Product With Id "productId" and has sufficient stock 
	 * 
	 */
	@Override
	public List<Vendor> getAllVendorListWithProductId(Integer productId){
	
		List<Vendor> list=vendorStockRepository.getVendorsWithId(productId);
		/*
		 * To Stop the cyclic calls between Vendor and VendorStockList Objects set the Property 
		 * stocks in Vendor object to null before sending the list
		 * 
		 */
		list=list.stream().map(x -> {
			x.setStocks(null);
			return x;
			}).collect(Collectors.toList());
		log.info("Venodr Details of the vendors who sell product "+productId+" fetched");
		return list;
	}
}