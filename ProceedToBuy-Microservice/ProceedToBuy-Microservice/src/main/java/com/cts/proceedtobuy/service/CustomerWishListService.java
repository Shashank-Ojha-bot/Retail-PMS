package com.cts.proceedtobuy.service;

import java.util.List;

import com.cts.proceedtobuy.model.CustomerWishlist;

public interface CustomerWishListService {

	public CustomerWishlist addToCustomerWishlist(CustomerWishlist customerWishlist);
	
	public List<CustomerWishlist> getCustomerWishList(String customerName);
}
