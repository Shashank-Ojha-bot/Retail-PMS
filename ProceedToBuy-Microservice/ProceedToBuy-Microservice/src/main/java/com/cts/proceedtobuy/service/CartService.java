package com.cts.proceedtobuy.service;

import java.util.List;

import com.cts.proceedtobuy.model.Cart;
import com.cts.proceedtobuy.dto.Vendor;

public interface CartService {
	
	public Cart addProductToCart(Cart cart,List<Vendor> listVendors);
	
	public List<Cart> getCarts(String customerName);
}
