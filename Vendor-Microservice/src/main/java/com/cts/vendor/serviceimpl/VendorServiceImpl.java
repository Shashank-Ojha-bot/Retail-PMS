package com.cts.vendor.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.vendor.exception.VendorNotFoundException;
import com.cts.vendor.model.Vendor;
import com.cts.vendor.repository.VendorRepository;
import com.cts.vendor.service.VendorService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VendorServiceImpl implements VendorService{

	@Autowired
	private VendorRepository vendorRepository;

	/*
	 * To Get Vendor Details by Id.
	 */
	@Override
	public Vendor getVendorById(Integer vendorId) {
	
		Vendor vendor=vendorRepository.findById(vendorId)
				.orElseThrow(() -> new VendorNotFoundException("Vendor with id "+vendorId+" not found"));
		log.info("Vendor Details : "+vendor);
		return vendor;
	}
}
