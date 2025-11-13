package com.mertalptekin.springproductservice.controller;


import com.mertalptekin.springproductservice.client.OrderClient;
import com.mertalptekin.springproductservice.dtos.GetOrderRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;


@RestController
@RequestMapping("api/v1")
public class HomeController {

    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final OrderClient orderClient;

    public HomeController(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder, OrderClient orderClient){
        this.discoveryClient = discoveryClient;
        this.restClient = restClientBuilder.build();
        this.orderClient = orderClient;
    }

    // Recilency4j ye client to client haberleşmesi olduğu yerlerde sadece ihtiyaç var.

    @Retry(name = "orderService",fallbackMethod = "retryFallback")
    @PostMapping("/openFeign")
    public String openFeign(@RequestBody GetOrderRequest request){
        return "call OrderService GET:" + orderClient.getOrderedRequest(request);
    }


    public String retryFallback(GetOrderRequest request, Throwable t) {
        return "RetryFallback" + t.getMessage();
    }

    @GetMapping
    public String index(){

        // Service to Service Communication
        // Bir OrderService birden fazla porttan çalıştırılabilir. Bu sebeple git çözümlediğin ikini getir.
        // config dosyası spring.application.name  -> serviceId çözümlemesi yapıyor.
        ServiceInstance service = discoveryClient.getInstances("spring-order-service").get(0);



        if(service != null) {
            // localhost ayrımı yapılmalı
            // canlıda service.getUri() üzerinden işlem yapmamız lazım
                String response = restClient.get().uri(service.getUri() + "/api/v1").retrieve().body(String.class);
                return "ProductService Get Request " + response;

        }
        else {
            return "Service Not Found";
        }


    }


}
