package com.cts.rpm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RetailProductManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetailProductManagementApplication.class, args);
	}

}
