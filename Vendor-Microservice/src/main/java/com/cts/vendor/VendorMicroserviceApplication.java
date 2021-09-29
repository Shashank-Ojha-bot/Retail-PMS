package com.cts.vendor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VendorMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendorMicroserviceApplication.class, args);
	}

}
