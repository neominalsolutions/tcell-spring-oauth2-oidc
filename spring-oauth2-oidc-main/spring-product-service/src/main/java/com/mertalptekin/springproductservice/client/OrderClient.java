package com.mertalptekin.springproductservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

// Eureka Server register olan servis ismimiz
@FeignClient(name = "spring-order-service")
public interface OrderClient {

    @GetMapping("api/v1")
    String getOrderedRequest();
}

// Spring Boot Application @EnableFeignClients