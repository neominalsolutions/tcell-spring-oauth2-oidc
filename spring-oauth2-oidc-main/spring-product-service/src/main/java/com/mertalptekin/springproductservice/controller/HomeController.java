package com.mertalptekin.springproductservice.controller;


import com.mertalptekin.springproductservice.client.OrderClient;
import com.mertalptekin.springproductservice.dtos.GetOrderRequest;
import com.mertalptekin.springproductservice.service.ProductService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.net.ConnectException;
import java.util.List;


@RestController
@RequestMapping("api/v1")
public class HomeController {

    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final ProductService productService;

    public HomeController(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder, ProductService productService){
        this.discoveryClient = discoveryClient;
        this.restClient = restClientBuilder.build();
        this.productService = productService;
    }

    // Recilency4j ye client to client haberleşmesi olduğu yerlerde sadece ihtiyaç var.


    @PostMapping("/openFeign")
    @RateLimiter(name = "orderServiceRateLimiter",fallbackMethod = "rateLimiter")
    public ResponseEntity<String> openFeign(@RequestBody GetOrderRequest request) {
        var response = productService.GetOrderedProduct(request);
        return  ResponseEntity.ok(response);
    }


    public ResponseEntity<String> rateLimiter(@RequestBody GetOrderRequest request, Throwable t)  {
        // logger
        // hata dışında eğer veri cachledeye response son güncel cache üzerinden döner.
        // Çok falza istek atıldı status code
        return new ResponseEntity<>(HttpStatusCode.valueOf(429));

    };


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
