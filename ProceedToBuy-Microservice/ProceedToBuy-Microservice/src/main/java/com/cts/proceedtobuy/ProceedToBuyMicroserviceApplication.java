package com.cts.proceedtobuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProceedToBuyMicroserviceApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ProceedToBuyMicroserviceApplication.class, args);
	}

}
