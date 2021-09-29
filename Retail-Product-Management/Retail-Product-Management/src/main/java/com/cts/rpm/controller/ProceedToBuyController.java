package com.cts.rpm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cts.rpm.feign.ProceedToBuyClient;
import com.cts.rpm.feign.ProductClient;
import com.cts.rpm.feign.VendorClient;
import com.cts.rpm.model.Cart;
import com.cts.rpm.model.CartDetails;
import com.cts.rpm.model.CustomerWishListDetails;
import com.cts.rpm.model.CustomerWishlist;
import com.cts.rpm.model.Product;
import com.cts.rpm.model.Vendor;

import feign.FeignException;

@Controller
public class ProceedToBuyController {

	@Autowired
	ProceedToBuyClient proceedToBuyClient;
	
	@Autowired
	ProductClient productClient;
	
	@Autowired VendorClient vendorClient;
	
	@GetMapping("/addToCart")
	public ModelAndView addToCart(HttpServletRequest request,Integer productId,@ModelAttribute Cart cart,@RequestParam("zip") Integer zip,
			@RequestParam("rating") Float rating) {
		
		if(request.getSession().getAttribute("Authorization") == null) {
			
			ModelAndView model=new ModelAndView("session-tiemout");
			return model;
		}
		
		ModelAndView model=new ModelAndView();
			Product product=productClient.getProductById(productId,(String) request.getSession().getAttribute("Authorization"));
			productClient.addProductRating(productId,rating,(String) request.getSession().getAttribute("Authorization"));
			try {
				cart.setCustomerName((String) request.getSession().getAttribute("userName"));
				cart.setProductId(productId);
				cart.setZipcode(zip);
				model.addObject("product",product);
				cart=proceedToBuyClient.addProductToCart((String) request.getSession().getAttribute("Authorization"), cart);
				model.setViewName("redirect:/viewAllCarts");
			}
			catch(FeignException ex) {
				if(ex.getMessage().contains("out of Stock")) {
					String str=ex.getMessage().split("message")[1].split(":")[1];
					String str1=str.substring(1,str.length()-3);
					System.out.println(str1);
					model.setViewName("error-page");
					model.addObject("errorMsg", str1);
					//model.addObject("link", "/portal/addToWishList?quantity=1&productId="+product.getId());
					model.addObject("productId", productId);
					model.addObject("quantity", 1);
					model.addObject("msg", "Add To WishList");
				}
				else {
				model.setViewName("error-page");
				model.addObject("errorMsg",ex.getMessage());
				}
			}
		
		return model;
	}
	
	@GetMapping("/viewAllCarts")
	public ModelAndView viewAllCarts(HttpServletRequest request) {
		
		if(request.getSession().getAttribute("Authorization") == null) {
			
			ModelAndView model=new ModelAndView("session-tiemout");
			return model;
		}
		
		ModelAndView model=new ModelAndView("cart");
		String customerName=(String)request.getSession().getAttribute("userName");
		List<Cart> listCarts=proceedToBuyClient.getCarts((String) request.getSession().getAttribute("Authorization"), customerName);
		
		if(listCarts.isEmpty()) {
			
			model.addObject("errorHead", "Sorry!");
			model.addObject("errorMessage", "Your Cart is Empty!!");
		}
		else {
		List<CartDetails> list=new ArrayList<CartDetails>();
		listCarts.stream().forEach((c) -> {
			
			Product p=productClient.getProductById(c.getProductId(), (String) request.getSession().getAttribute("Authorization"));
			Vendor v=vendorClient.getVendorDetailsById(c.getVendorId(), (String) request.getSession().getAttribute("Authorization"));
			CartDetails cd=CartDetails.builder()
				.cartId(c.getCartId())
				.customerName(c.getCustomerName())
				.zipcode(c.getZipcode())
				.deliveryDate(c.getDeliveryDate())
				.product(p)
				.vendor(v).build();
			list.add(cd);	
		});
		model.addObject("cartList", list);
		}
		
	
		return model;
	}
	
	@GetMapping("/addToWishList")
	public ModelAndView addToWishList(HttpServletRequest request,@RequestParam Integer quantity,@RequestParam Integer productId) {
		
		if(request.getSession().getAttribute("Authorization") == null) {
			
			ModelAndView model=new ModelAndView("session-tiemout");
			return model;
		}
		
		CustomerWishlist customerWishlist=new CustomerWishlist();
		customerWishlist.setCustomerName((String)request.getSession().getAttribute("userName"));
		customerWishlist.setProductId(productId);
		customerWishlist.setQuantity(quantity);
		proceedToBuyClient.addProductToWishlist((String)request.getSession().getAttribute("Authorization"), customerWishlist);
		ModelAndView model=new ModelAndView("redirect:/viewWishList");
		return model;
	}
	
	@GetMapping("/viewWishList")
	public ModelAndView getWishList(HttpServletRequest request) {
		
		if(request.getSession().getAttribute("Authorization") == null) {
			
			ModelAndView model=new ModelAndView("session-tiemout");
			return model;
		}
		
		ModelAndView model=new ModelAndView("wishlist");
		List<CustomerWishlist> wishList=new ArrayList<CustomerWishlist>();
		wishList=proceedToBuyClient.getCustomerWishList((String)request.getSession().getAttribute("Authorization"),
				(String)request.getSession().getAttribute("userName"));
		
		if(wishList.isEmpty()) {
			
			model.addObject("errorHead", "Sorry!");
			model.addObject("errorMEssage", "Your Wishlist is Empty!!");
		}
		else {
		List<CustomerWishListDetails> list=new ArrayList<CustomerWishListDetails>();
		wishList.stream().forEach((cwl)-> {
			Product p=productClient.getProductById(cwl.getProductId(),
					(String)request.getSession().getAttribute("Authorization"));
			CustomerWishListDetails cwld=CustomerWishListDetails.builder()
					.customerName(cwl.getCustomerName())
					.product(p)
					.dateAddedToWishlist(cwl.getDateAddedToWishlist())
					.quantity(cwl.getQuantity()).build();
			list.add(cwld);
			System.out.println(cwld);
		});
		model.addObject("wishList", list);
		}

		return model;
	}
}
