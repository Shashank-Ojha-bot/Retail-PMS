package com.cts.proceedtobuy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cts.proceedtobuy.model.CustomerWishlist;
import com.cts.proceedtobuy.repository.CustomerWishlistRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerWishListServiceImplTest {

	@MockBean
	private CustomerWishlistRepository cwlRepo;
	
	@Autowired
	private CustomerWishListService customerWishListService;
	
	@Test
	void testGetCustomerWishList() throws Exception{
		String customerName="abc";
		List<CustomerWishlist> cwl=Stream.of(new CustomerWishlist(1,"abc",1,10,LocalDateTime.of(2017,2,3,4,4)),
				new CustomerWishlist(2,"abc",1,10,LocalDateTime.of(2017,2,3,4,4))).collect(Collectors.toList());
		when(cwlRepo.findByCustomerName(customerName)).thenReturn((List<CustomerWishlist>)cwl);
		assertEquals(customerWishListService.getCustomerWishList(customerName),cwl);
	}
	
	@Test
	void testAddToCustomerWishList() throws Exception{
		String customerName="abc";
		CustomerWishlist cwl=new CustomerWishlist();
		cwl.setCustomerName(customerName);
		cwl.setId(1);
		cwl.setProductId(1);
		cwl.setQuantity(1);
		when(cwlRepo.save(Mockito.any(CustomerWishlist.class)))
        .thenAnswer(i -> i.getArguments()[0]);
		assertEquals(cwl,customerWishListService.addToCustomerWishlist(cwl));
		
	}
}
