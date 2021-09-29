package com.cts.proceedtobuy.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.proceedtobuy.model.CustomerWishlist;
import com.cts.proceedtobuy.repository.CustomerWishlistRepository;
import com.cts.proceedtobuy.service.CustomerWishListService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerWishListServiceImpl implements CustomerWishListService{

	
	@Autowired
	private CustomerWishlistRepository customerWishlistRepository;
	
	/*
	 * To Add Product To The Wish List
	 */
	@Override
	public CustomerWishlist addToCustomerWishlist(CustomerWishlist customerWishlist) {
		
		log.info("addToCustomerWishlist Service method called");
		//Set Date added to wish list as Today's date
		customerWishlist.setDateAddedToWishlist(LocalDateTime.now());
		CustomerWishlist ob=customerWishlistRepository.save(customerWishlist);
		log.info("Product Added to Customer Wish List : "+ob);
		return ob;
	}

	/*
	 * To get Customer Wish List
	 */
	@Override
	public List<CustomerWishlist> getCustomerWishList(String customerName) {
		// TODO Auto-generated method stub
		
		log.info("getCustomerWishList Service method called");
		return customerWishlistRepository.findByCustomerName(customerName);
	}

}
