package com.mertalptekin.springproductservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringProductServiceApplication.class, args);
	}

}
