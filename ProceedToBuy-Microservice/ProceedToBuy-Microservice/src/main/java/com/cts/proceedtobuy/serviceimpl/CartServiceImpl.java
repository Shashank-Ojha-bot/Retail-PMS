package com.cts.proceedtobuy.serviceimpl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.proceedtobuy.exception.ProductOutOfStockException;
import com.cts.proceedtobuy.model.Cart;
import com.cts.proceedtobuy.dto.Vendor;
import com.cts.proceedtobuy.repository.CartRepository;
import com.cts.proceedtobuy.service.CartService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartServiceImpl implements CartService{
	
	@Autowired
	CartRepository cartrepo;
	
	/*
	 * To Add Product In to the cart. It saves cart object into the repository.
	 */
	@Override
	public Cart addProductToCart(Cart cart,List<Vendor> listVendors) {
		
		if(listVendors.isEmpty()) {
			throw new ProductOutOfStockException("Product is out of Stock");
		}
		else {
			
			log.info("addProductToCart Service method called");
			//Assigns a vendor to the product whose rating is more.
			Vendor vendor=listVendors.stream().max(Comparator.comparingDouble(Vendor::getVendorRating)).get();
			log.info("Details of Vendor Assigned for the delivery "+vendor);
			cart.setVendorId(vendor.getVendorId());
			
			//Set delivery Date 3 days after the date of order.
			cart.setDeliveryDate(LocalDateTime.now().plusDays(3));
			cart=cartrepo.save(cart);
			log.info("Cart added to te repository : "+cart);
			return cart;
		}
		
	}

	/*
	 * To get All the carts of a Customer
	 */
	@Override
	public List<Cart> getCarts(String customerName) {
		
		log.info("getCarts service method called");
		return cartrepo.findByCustomerName(customerName);
	}

}
