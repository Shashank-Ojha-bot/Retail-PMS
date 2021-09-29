package com.cts.vendor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.cts.vendor.exception.VendorNotFoundException;
import com.cts.vendor.model.Vendor;
import com.cts.vendor.repository.VendorRepository;

@SpringBootTest
public class VendorServiceImplTest {
	
	@Autowired
	private VendorService vendorService;
	
	@MockBean
	private VendorRepository vendorRepository;
	
	@Test
	void testgetVendorById() throws Exception{
		Vendor vendor = new Vendor(1, "Amazon", 30.5f, 5.0f);
		Integer vendorId = 1;
		when(vendorRepository.findById(vendorId)).thenReturn(Optional.of(vendor));
		assertEquals(vendorService.getVendorById(vendorId).getVendorId(), vendor.getVendorId());
	}
	
	@Test
	void testgetVendorByIdVendorNotFoundException() throws Exception{
		//Integer vendorId=101;
		when(vendorRepository.findById(101)).thenReturn(Optional.empty());
		Exception exception = assertThrows(VendorNotFoundException.class, () -> vendorService.getVendorById(101));
		assertEquals("Vendor with id 101 not found", exception.getMessage());
	}

}