package com.cts.proceedtobuy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cts.proceedtobuy.model.Cart;
import com.cts.proceedtobuy.model.CustomerWishlist;
import com.cts.proceedtobuy.dto.Vendor;
import com.cts.proceedtobuy.feign.AuthorisingClient;
import com.cts.proceedtobuy.feign.VendorClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {
	@Autowired
	private MockMvc mock;

	@MockBean
	private AuthorisingClient authorisingClient;
	
	@MockBean
	private VendorClient vendorClient;

	private String apiVersion="/api/v1";
	
	private static Cart cart;
	
	private static CustomerWishlist cwl;
	
	@BeforeAll
	static void setCart() {
		
		cart=new Cart();
		cart.setCartId(1);
		cart.setCustomerName("abc");
		cart.setProductId(1);
		cart.setZipcode(123456);
		
		cwl=new CustomerWishlist();
		cwl.setCustomerName("abc");
		cwl.setId(1);
		cwl.setProductId(1);
		cwl.setQuantity(1);
	}
	
	@Test
	void testaddProductToCart() throws Exception {
		when(authorisingClient.authorizeTheRequest("Bearer hello.hello.hello")).thenReturn(true);
		
		List<Vendor> vendorList = Stream.of(new Vendor(1, "Amazon", 30.5f, 5.0f),
				new Vendor(2, "Google", 3.5f, 4.0f))
				.collect(Collectors.toList());
		 String jsonCart = this.mapToJson(cart);
		
		when(vendorClient.getVendorDetails(1, "Bearer hello.hello.hello")).thenReturn((List<Vendor>)vendorList);
		MvcResult mvcResult = mock
				.perform(post(apiVersion+"/addProductToCart").contentType("application/json").content(jsonCart).header("Authorization", "Bearer hello.hello.hello")).andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		assertTrue( mvcResult.getResponse().getContentAsString().contains("1"));

	}
	
	@Test
	void testaddProductToCartProductOutOfStockException() throws Exception {
		
		when(authorisingClient.authorizeTheRequest("Bearer hello.hello.hello")).thenReturn(true);
		when(vendorClient.getVendorDetails(1, "Bearer hello.hello.hello")).thenReturn((new ArrayList<Vendor>()));
		String jsonCart = this.mapToJson(cart);
		MvcResult mvcResult = mock
				.perform(post(apiVersion+"/addProductToCart").contentType("application/json").content(jsonCart).header("Authorization", "Bearer hello.hello.hello")).andReturn();
		assertEquals(404, mvcResult.getResponse().getStatus());
	}
	
	@Test
	void testaddProductToCartInvalidAuthorization() throws Exception{
		when(authorisingClient.authorizeTheRequest("InvalidToken")).thenReturn(false);
		
		String jsonCart = this.mapToJson(cart);
	    mock.perform(post(apiVersion+"/addProductToCart").contentType("application/json").content(jsonCart).header("Authorization", "InvalidToken")).
	       		andExpect(status().isForbidden());
	       
	   }
	
	@Test
	void testddProductToCartWithoutHeader() throws Exception {
		
		String jsonCart = this.mapToJson(cart);
		mock.perform(post(apiVersion+"/addProductToCart").contentType("application/json").content(jsonCart)).andExpect(status().isBadRequest());

	}
	
	@Test
	void testaddProductToWishlist() throws Exception{
		when(authorisingClient.authorizeTheRequest("Bearer hello.hello.hello")).thenReturn(true);
		
		String jsonCwl = this.mapToJson(cwl);
		MvcResult mvcResult = mock
				.perform(post(apiVersion+"/addProductToWishlist").contentType("application/json").content(jsonCwl).header("Authorization", "Bearer hello.hello.hello")).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
		
	}
	
	@Test
	void testaddProductToWishlistInvalidAuthorization() throws Exception{
		
		String jsonCwl = this.mapToJson(cwl);
		mock.perform(post(apiVersion+"/addProductToWishlist").contentType("application/json").content(jsonCwl).header("Authorization", "InvalidToken"))
		       .andExpect(status().isForbidden());
	}
	
	@Test
	void testaddProductToWishlistWithoutHeader() throws Exception{
		
		String jsonCwl = this.mapToJson(cwl);
		mock.perform(post(apiVersion+"/addProductToWishlist").contentType("application/json").content(jsonCwl)).andExpect(status().isBadRequest());
	}
	
	@Test
	void testgetCarts() throws Exception{
		String customerName="abc";
		when(authorisingClient.authorizeTheRequest("Bearer hello.hello.hello")).thenReturn(true);
		MvcResult mvcResult = mock.perform(get(apiVersion+"/getCarts/" + customerName).header("Authorization", "Bearer hello.hello.hello")).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
		
	}
	
	@Test
    void testgetCartsInvalidAuthorization() throws Exception {
		String customerName="abc";
	    when(authorisingClient.authorizeTheRequest("InvalidToken")).thenReturn(false);
	    mock.perform(get(apiVersion+"/getCarts/" + customerName).header("Authorization", "InvalidToken")).
	    	andExpect(status().isForbidden());
       
    }
	
	@Test
    void testgetCartsWithoutHeader() throws Exception{
		String customerName="abc";
		mock.perform(get(apiVersion+"/getCarts/" + customerName)).andExpect(status().isBadRequest());

	}
	
	@Test
	void testCustomerWishList() throws Exception{
		String customerName="abc";
		when(authorisingClient.authorizeTheRequest("Bearer hello.hello.hello")).thenReturn(true);
		MvcResult mvcResult = mock.perform(get(apiVersion+"/getCustomerWishList/" + customerName).header("Authorization", "Bearer hello.hello.hello")).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
		
	}
	
	@Test
    void testgetCustomerWishListInvalidAuthorization() throws Exception {
		String customerName="abc";
	    when(authorisingClient.authorizeTheRequest("InvalidToken")).thenReturn(false);
	    mock.perform(get(apiVersion+"/getCustomerWishList/" + customerName).header("Authorization", "InvalidToken")).
	    	andExpect(status().isForbidden());
       
    }
	
	@Test
    void testgetCustomerWishListWithoutHeader() throws Exception{
		String customerName="abc";
		mock.perform(get(apiVersion+"/getCustomerWishList/" + customerName)).andExpect(status().isBadRequest());

	}
	
	String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
