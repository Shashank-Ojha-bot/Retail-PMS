package com.cts.proceedtobuy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cts.proceedtobuy.exception.ProductOutOfStockException;
import com.cts.proceedtobuy.model.Cart;

import com.cts.proceedtobuy.dto.Vendor;
import com.cts.proceedtobuy.repository.CartRepository;

@SpringBootTest
public class CartServiceImplTest {
	
	@MockBean
	CartRepository cartRepo;
	
	@Autowired
	private CartService cartService;
	
	private static Cart cart;
	
	@BeforeAll
	static void setCart() {
		
		cart=new Cart();
		cart.setCartId(1);
		cart.setCustomerName("abc");
		cart.setProductId(1);
		cart.setZipcode(123456);
	}
		
	
	@Test
	void testaddProductToCartProductOutOfStockException() throws Exception{

		assertThrows(ProductOutOfStockException.class, () -> cartService.addProductToCart(cart, new ArrayList<Vendor>()));
		//when(cartrepo.).thenReturn(Optional.empty());
	}
	
	@Test
	void testaddProductToCart() throws Exception{
		//Integer vendorId=1;
		List<Vendor> vendorList = Stream.of(new Vendor(1, "Amazon", 30.5f, 5.0f),
				new Vendor(2, "Google", 3.5f, 4.0f))
				.collect(Collectors.toList());
		
		when(cartRepo.save(Mockito.any(Cart.class)))
        .thenAnswer(i -> i.getArguments()[0]);
		Cart result=cartService.addProductToCart(cart, vendorList);
		assertEquals(1,result.getVendorId());
	}
	
	@Test
	void testgetCarts() throws Exception {
		String customerName="abc";
		List<Cart> cartList = Stream.of(new Cart(1,1,123456,"Amazon",LocalDateTime.of(2017,2,3,4,4),5),
				new Cart(2,1,123456,"Google",LocalDateTime.of(2017,3,2,4,4),5))
				.collect(Collectors.toList());
		when(cartRepo.findByCustomerName(customerName)).thenReturn((List<Cart>)cartList);
		assertEquals(cartService.getCarts(customerName),cartList);
	}

	
}
