package com.cts.proceedtobuy.feign;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cts.proceedtobuy.dto.Vendor;

@FeignClient(name = "Vendor-Microservice", url = "${vendor.url}")
@RequestMapping("${vendor.apiversion}")
public interface VendorClient {
	
	@GetMapping("/getVendorDetails/{productId}")
	public List<Vendor> getVendorDetails(@PathVariable int productId,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader);

}
