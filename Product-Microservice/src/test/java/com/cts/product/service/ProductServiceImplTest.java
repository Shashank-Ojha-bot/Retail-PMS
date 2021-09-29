package com.cts.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cts.product.exception.InvalidRatingException;
import com.cts.product.exception.ProductNotFoundException;
import com.cts.product.model.Product;
import com.cts.product.repository.ProductRepository;

@SpringBootTest
public class ProductServiceImplTest {

	@Autowired
	private ProductService productService;
	
	@MockBean
	private ProductRepository productRepo;
	
	@Test
	void testSearchProductByIdExists() throws Exception {
		Product product = new Product(1,200f,"Product 1","This is product 1","image url",4f);
		when(productRepo.findById(1)).thenReturn(Optional.of(product));
		assertEquals(productService.getProductById(1).getName(),product.getName());
	}

	@Test
	void testSearchProductByIdNotExist() throws Exception {
		when(productRepo.findById(101)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ProductNotFoundException.class, () -> productService.getProductById(101));
		assertEquals("Product with id 101 not found", exception.getMessage());
	}

	@Test
	void testSearchProductByNameExists() throws Exception {
		Product product = new Product(1,200f,"Product 1","This is product 1","image url",4f);
		Product product2 =new Product(2,200f,"Product 2","This is product 2","image url",4f);
		//Product product3 = new Product(3,200f,"vishesh 1","This is product 3","image url",4f);
		List<Product> list=new ArrayList<Product>();
		list.add(product2);
		list.add(product);
		when(productRepo.findByNameContainsIgnoreCase("Product")).thenReturn(list);
		assertEquals(productService.getProductByName("Product").size(), 2);
	}

	@Test
	void testSearchProductByNameNotExist() throws Exception {
		when(productRepo.findByNameContainsIgnoreCase("XYZ")).thenReturn(new ArrayList<Product>());
		Exception exception = assertThrows(ProductNotFoundException.class,
				() -> productService.getProductByName("XYZ"));
		Exception exception1 = assertThrows(ProductNotFoundException.class,
				() -> productService.getProductByName(""));
		
		assertEquals("Product with name XYZ not Found", exception.getMessage());
		assertEquals("Product with name  not Found", exception1.getMessage());
	}
	

	@Test
	void testAddProductRating() throws Exception {
		Product product = new Product(1,200f,"Product 1","This is product 1","image url",4f);
		when(productRepo.findById(1)).thenReturn(Optional.of(product));
		productService.addProductRating(1, 3f);
		assertEquals(3, productService.getProductById(1).getRating());
		
	}

	@Test
	void testAddProductRatingProductNotFoundException() throws Exception {
		when(productRepo.findById(101)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ProductNotFoundException.class,
				() -> productService.addProductRating(101, 5f));
		assertEquals("Product with id 101 not found", exception.getMessage());
	}

	@Test
	void testAddProductRatingInvalidRatingExceptionGreterThan5() throws Exception {
		Product product = new Product(1,200f,"Product 1","This is product 1","image url",4f);
		when(productRepo.findById(1)).thenReturn(Optional.of(product));
		Exception exception = assertThrows(InvalidRatingException.class,
				() -> productService.addProductRating(1, 7f));
		assertEquals("7.0 is Invalid rating. Rating should be greater than 0 and less than than 5", exception.getMessage());
	}
	
	@Test
	void testAddProductRatingInvalidRatingExceptionLessThanEqual0() throws Exception {
		Product product = new Product(1,200f,"Product 1","This is product 1","image url",4f);
		when(productRepo.findById(1)).thenReturn(Optional.of(product));
		Exception exception = assertThrows(InvalidRatingException.class,
				() -> productService.addProductRating(1, 0f));
		assertEquals("0.0 is Invalid rating. Rating should be greater than 0 and less than than 5", exception.getMessage());
	}

	
	@Test
	void testGetAll() {
		List<Product> products = Stream.of(new Product(1,200f,"Product 1","This is product 1","image url",4f),
				new Product(2,200f,"Product 2","This is product 2","image url",4f))
				.collect(Collectors.toList());
		when(productRepo.findAll()).thenReturn((List<Product>) products);
		assertEquals(productService.getAllProducts().get(0).getName(), ((List<Product>) products).get(0).getName());
	} 

}
