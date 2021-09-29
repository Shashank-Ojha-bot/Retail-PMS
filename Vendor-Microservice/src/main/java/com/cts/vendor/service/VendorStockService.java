package com.cts.vendor.service;

import java.util.List;

import com.cts.vendor.model.Vendor;

public interface VendorStockService {

	public List<Vendor> getAllVendorListWithProductId(Integer productId);
	
	
}
