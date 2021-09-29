package com.cts.vendor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cts.vendor.model.Vendor;
import com.cts.vendor.repository.VendorStockRepository;

@SpringBootTest
public class VendorStockServiceImplTest {

	@Autowired
	VendorStockService vendorStockService;
	
	@MockBean
	VendorStockRepository vendorStockRepository;
	
	@Test
	public void testgetAllVendorListWithProductId() throws Exception {
		Integer productId=1;
		List<Vendor> vendorList = Stream.of(new Vendor(1, "Amazon", 30.5f, 5.0f),
				new Vendor(2, "Google", 3.5f, 4.0f))
				.collect(Collectors.toList());
		when(vendorStockRepository.getVendorsWithId(productId)).thenReturn((List<Vendor>) vendorList);
		assertEquals(vendorStockService.getAllVendorListWithProductId(productId), vendorList);
		
	}
}