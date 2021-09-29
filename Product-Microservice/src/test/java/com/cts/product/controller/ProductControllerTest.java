package com.cts.product.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cts.product.feign.AuthorisingClient;
import com.cts.product.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

	 @Autowired
	    private MockMvc mock;
	 
	 @MockBean
	 private AuthorisingClient authorisingClient;
	 
	private String apiVersion="/api/v1";

	    @Test
	     void testSearchProductById() throws Exception {
	        int productId = 1;
	        when(authorisingClient.authorizeTheRequest("Bearer hello.hello.hello")).thenReturn(true);
	        MvcResult mvcResult = mock.perform(get(apiVersion+"/searchProductById/" + productId).header("Authorization", "Bearer hello.hello.hello")).andReturn();
	        assertEquals(200,mvcResult.getResponse().getStatus());
	    }
	    

	    @Test()
	     void testSearchProductByIdProductNotFoundException() throws Exception {
	        int productId = 101;
	        when(authorisingClient.authorizeTheRequest("Bearer hello.hello.hello")).thenReturn(true);
	        MvcResult mvcResult = mock.perform(get(apiVersion+"/searchProductById/" + productId).header("Authorization", "Bearer hello.hello.hello")).andReturn();
	        assertEquals(404, mvcResult.getResponse().getStatus());
	        assertEquals(true,
	                mvcResult.getResponse().getContentAsString().contains("Product with id " + productId + " not found"));
	    }
	    
	    @Test
	     void testSearchProductByIdInvalidAuthorization() throws Exception {
	        when(authorisingClient.authorizeTheRequest("InvalidToken")).thenReturn(false);
	        mock.perform(get(apiVersion+"/searchProductById/" + 1).header("Authorization", "InvalidToken")).
	        		andExpect(status().isForbidden());
	        
	    }

	    @Test
		void testsearchProductByIdWithoutHeader() throws Exception {

	    	mock.perform(get(apiVersion+"/searchProductById/" + 1)).andExpect(status().isBadRequest());

		}
	    
	    @Test
		void testsearchProductByIdFeignException() throws Exception {
	    	when(authorisingClient.authorizeTheRequest("Token")).thenThrow(FeignException.class);
	    	mock.perform(get(apiVersion+"/searchProductById/" + 1)).andExpect(status().isBadRequest());
		}

	    @Test
	     void testSearchProductByName() throws Exception {
	        String productName = "Mobile";
	        when(authorisingClient.authorizeTheRequest("Bearer hello.hello.hello")).thenReturn(true);
	        MvcResult mvcResult = mock.perform(get(apiVersion+"/searchProductName/" + productName).header("Authorization", "Bearer hello.hello.hello")).andReturn();
	        assertEquals(true, mvcResult.getResponse().getContentAsString().contains(productName));
	    }

	    @Test
	     void testSearchProductByNameProductNotFoundException() throws Exception {
	        String productName = "XYZ";
	        when(authorisingClient.authorizeTheRequest("Bearer hello.hello.hello")).thenReturn(true);
	        MvcResult mvcResult = mock.perform(get(apiVersion+"/searchProductName/" + productName).header("Authorization", "Bearer hello.hello.hello")).andReturn();
	        assertEquals(404, mvcResult.getResponse().getStatus());
	        assertEquals(true, mvcResult.getResponse().getContentAsString()
	                .contains("Product with name " + productName + " not Found"));
	    }
	    
	    @Test
	     void testSearchProductByNameInvalidAuthorization() throws Exception {
	        when(authorisingClient.authorizeTheRequest("InvalidToken")).thenReturn(false);
	        mock.perform(get(apiVersion+"/searchProductName/" + "Product 1").header("Authorization", "InvalidToken")).
	        		andExpect(status().isForbidden());
	        
	    }

	    @Test
		void testsearchProductByNameWithoutHeader() throws Exception {

	    	mock.perform(get(apiVersion+"/searchProductName/" + "Product 1")).andExpect(status().isBadRequest());

		}

	    @Test
		void testsearchProductByNameFeignException() throws Exception {
	    	when(authorisingClient.authorizeTheRequest("Token")).thenThrow(FeignException.class);
	    	mock.perform(get(apiVersion+"/searchProductName/" + "Product 1")).andExpect(status().isBadRequest());
		}

	    @Test
	     void testAddProductRating() throws Exception {
	    	 when(authorisingClient.authorizeTheRequest("Bearer hello.hello.hello")).thenReturn(true);
	        Product product = new Product(3, 200f,"Tablet", "imageName", "abc_image", 3f);
	        String jsonProduct = this.mapToJson(product);
	        MvcResult mvcResult = mock
	                .perform(post(apiVersion+"/addProductRating/1/3").contentType("application/json").content(jsonProduct).header("Authorization", "Bearer hello.hello.hello")).andReturn();
	        assertEquals(200, mvcResult.getResponse().getStatus());
	    }

	    @Test
	     void testAddProductRatingProductNotFoundException() throws Exception {
	        Product product = new Product(100, 200f,"Tablet", "imageName", "abc_image", 3f);
	        when(authorisingClient.authorizeTheRequest("Bearer hello.hello.hello")).thenReturn(true);
	        String jsonProduct = this.mapToJson(product);
	        MvcResult mvcResult = mock
	                .perform(post(apiVersion+"/addProductRating/100/3").contentType("application/json").content(jsonProduct).header("Authorization", "Bearer hello.hello.hello")).andReturn();
	        assertEquals(404, mvcResult.getResponse().getStatus());
	        assertEquals(true, mvcResult.getResponse().getContentAsString()
	                .contains("Product with id " + product.getId() + " not found"));
	    }

	    @Test
	     void testAddProductRatingGreaterThan5Exception() throws Exception {
	    	 when(authorisingClient.authorizeTheRequest("Bearer hello.hello.hello")).thenReturn(true);
	        Product product = new Product(3, 200f,"Tablet", "imageName", "abc_image", 3f);
	        String jsonProduct = this.mapToJson(product);
	        MvcResult mvcResult = mock
	                .perform(post(apiVersion+"/addProductRating/1/10").contentType("application/json").content(jsonProduct).header("Authorization", "Bearer hello.hello.hello")).andReturn();
	        assertEquals(406, mvcResult.getResponse().getStatus());
	        //assertEquals(true, mvcResult.getResponse().getContentAsString().contains("Rating should be between 1 to 5"));
	    }

	    @Test
	     void testAddProductRatingInvalidAuthorization() throws Exception {
	        when(authorisingClient.authorizeTheRequest("InvalidToken")).thenReturn(false);
	        mock.perform(post(apiVersion+"/addProductRating/1/4").header("Authorization", "InvalidToken")).
	        		andExpect(status().isForbidden());
	        
	    }
	    
	    @Test
		void testAddProductRatingWithoutHeader() throws Exception {

	    	mock.perform(post(apiVersion+"/addProductRating/1/4")).andExpect(status().isBadRequest());

		}
	    
	    @Test
		void testAddProductRatingFeignException() throws Exception {
	    	when(authorisingClient.authorizeTheRequest("Token")).thenThrow(FeignException.class);
	    	mock.perform(post(apiVersion+"/addProductRating/1/4")).andExpect(status().isBadRequest());
		}

	    
	    @Test
	    void testGetAllProducts() throws Exception {
	    	
	    	/*List<Product> products = Stream.of(new Product(3, 200f,"Tablet", "imageName", "abc_image", 3f),
	    			new Product(3, 200f,"Tablet", "imageName", "abc_image", 3f))
					.collect(Collectors.toList());*/
	    	
	    	 when(authorisingClient.authorizeTheRequest("Bearer hello.hello.hello")).thenReturn(true);
	    	mock.perform(get(apiVersion+"/getAllProdcuts").header("Authorization", "Bearer hello.hello.hello"))
	    		.andExpect(status().isOk());
	    }

	    @Test
	    void testGetAllProductsInvalidAuthorization() throws Exception{
	    	
	    	when(authorisingClient.authorizeTheRequest("InvalidToken")).thenReturn(false);
	        mock.perform(get(apiVersion+"/getAllProdcuts").header("Authorization", "InvalidToken")).
	        		andExpect(status().isForbidden());
	    }
	    
	    @Test
		void testGetAllProductsWithoutHeader() throws Exception {

	    	mock.perform(get(apiVersion+"/getAllProdcuts")).andExpect(status().isBadRequest());

		}
	    
	    @Test
		void testGetAllProductsFeignException() throws Exception {
	    	when(authorisingClient.authorizeTheRequest("Token")).thenThrow(FeignException.class);
	    	mock.perform(get(apiVersion+"/getAllProdcuts")).andExpect(status().isBadRequest());
		}


	    // JsonMapper
	     String mapToJson(Object object) throws JsonProcessingException {
	        ObjectMapper objectMapper = new ObjectMapper();
	        return objectMapper.writeValueAsString(object);
	    }
}
