package com.mertalptekin.springproductservice.client;

import com.mertalptekin.springproductservice.dtos.GetOrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Eureka Server register olan servis ismimiz
@FeignClient(name = "spring-order-service")
public interface OrderClient {

    @PostMapping("api/v1")
    String getOrderedRequest(@RequestBody GetOrderRequest request);
}

// Spring Boot Application @EnableFeignClients