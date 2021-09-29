package com.cts.vendor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cts.vendor.exception.ProductNotFoundException;
import com.cts.vendor.feign.AuthorisingClient;
import com.cts.vendor.feign.ProductClient;
import com.cts.vendor.dto.Product;
import com.cts.vendor.service.VendorStockService;

import feign.FeignException;

@SpringBootTest
@AutoConfigureMockMvc
public class VendorControllerTest {
	
	@Autowired
	private MockMvc mock;
	@MockBean
	private VendorStockService vendorStockService;
	@MockBean
	private ProductClient productClient;  
	
	@MockBean
	private AuthorisingClient authorisingClient;
	
	private String apiVersion="/api/v1";
	
	@Test
	void testgetVendorDetails() throws Exception {
		Integer productId = 3;
		when(authorisingClient.authorizeTheRequest("Bearer hello.hello.hello")).thenReturn(true);
		when(productClient.getProductById(productId, "Bearer hello.hello.hello")).thenReturn(new Product(1,200f,"","","",4f));
		MvcResult mvcResult = mock.perform(get(apiVersion+"/getVendorDetails/" + productId).header("Authorization", "Bearer hello.hello.hello")).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
//		assertEquals(true, mvcResult.getResponse().getContentAsString().contains("vendor 1"));
	}
	
	@Test
    void testgetVendorDetailsInvalidAuthorization() throws Exception {
       when(authorisingClient.authorizeTheRequest("InvalidToken")).thenReturn(false);
       mock.perform(get(apiVersion+"/getVendorDetails/" + 1).header("Authorization", "InvalidToken")).
       		andExpect(status().isForbidden());
       
   }
	
	@Test
	void testgetVendorDetailsProductNotFoundException() throws Exception{
		Integer productId = 1;
		when(authorisingClient.authorizeTheRequest("Bearer hello.hello.hello")).thenReturn(true);
		when(productClient.getProductById(productId, "Bearer hello.hello.hello")).thenThrow(ProductNotFoundException.class);
		MvcResult mvcResult = mock.perform(get(apiVersion+"/getVendorDetails/" + productId).header("Authorization", "Bearer hello.hello.hello")).andReturn();
		assertEquals(404, mvcResult.getResponse().getStatus());
		//assertEquals(true, mvcResult.getResponse().getContentAsString().contains("Product with id "+productId+" not found"));
	}
	
	@Test
	void testgetVendorDetailsWithoutHeader() throws Exception {

    	mock.perform(get(apiVersion+"/getVendorDetails/" + 1)).andExpect(status().isBadRequest());

	}
	
	@Test
	void testgetVendorDetailsFeignException() throws Exception {
    	when(authorisingClient.authorizeTheRequest("Token")).thenThrow(FeignException.class);
    	mock.perform(get(apiVersion+"/getVendorDetails/" + 1)).andExpect(status().isBadRequest());
	}
	
	@Test
	void testgetVendorDetailsById() throws Exception{
		Integer vendorId=1;
		when(authorisingClient.authorizeTheRequest("Bearer hello.hello.hello")).thenReturn(true);
		MvcResult mvcResult = mock.perform(get(apiVersion+"/getVendorDetailsById/" + vendorId).header("Authorization", "Bearer hello.hello.hello")).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
    void testgetVendorDetailsByIdInvalidAuthorization() throws Exception {
       when(authorisingClient.authorizeTheRequest("InvalidToken")).thenReturn(false);
       mock.perform(get(apiVersion+"/getVendorDetailsById/" + 1).header("Authorization", "InvalidToken")).
       		andExpect(status().isForbidden());
       
	}
	
	@Test
	void testgetVendorDetailsByIdVendorNotFoundException() throws Exception{
		Integer vendorId=100;
		when(authorisingClient.authorizeTheRequest("Bearer hello.hello.hello")).thenReturn(true);
		MvcResult mvcResult = mock.perform(get(apiVersion+"/getVendorDetailsById/" + vendorId).header("Authorization", "Bearer hello.hello.hello")).andReturn();
		assertEquals(404, mvcResult.getResponse().getStatus());
        assertEquals(true, mvcResult.getResponse().getContentAsString()
                .contains("Vendor with id "+vendorId+" not found"));
	}
	
	 @Test
	 void testgetVendorDetailsByIdWithoutHeader() throws Exception {

	    mock.perform(get(apiVersion+"/getVendorDetailsById/" + 1)).andExpect(status().isBadRequest());

	}
	 
	 @Test
	void testgetVendorDetailsByIdFeignException() throws Exception {
    	when(authorisingClient.authorizeTheRequest("Token")).thenThrow(FeignException.class);
	    mock.perform(get(apiVersion+"/getVendorDetailsById/" + 1)).andExpect(status().isBadRequest());
	}
	    
}