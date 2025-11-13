package com.mertalptekin.springproductservice.service;

import com.mertalptekin.springproductservice.client.OrderClient;
import com.mertalptekin.springproductservice.dtos.GetOrderRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

// Not:   <dependency>
//            <groupId>org.springframework.boot</groupId>
//            <artifactId>spring-boot-starter-aop</artifactId>
//        </dependency>

// yukarıdaki dependecy olmadan fallbackMethod tetikleniyor eklemeyi unutmayalım.

@Service
public class ProductService {

    private final OrderClient orderClient;

    public ProductService(OrderClient orderClient){
        this.orderClient = orderClient;
    }


    @Retry(name = "orderServiceRetry", fallbackMethod = "retry")
    @CircuitBreaker(name = "orderServiceCircuitBraker", fallbackMethod = "circuitBraker")
    public String GetOrderedProduct(GetOrderRequest request){
        var response = orderClient.getOrderedRequest(request);
        return response;
    }


    public String circuitBraker(@RequestBody GetOrderRequest request, Throwable t)  {
        // logger
        // hata dışında eğer veri cachledeye response son güncel cache üzerinden döner.
        return "CircuitBraker is Open State";
    };

    public String retry(@RequestBody GetOrderRequest request, Throwable t)  {
        // logger
        // hata dışında eğer veri cachledeye response son güncel cache üzerinden döner.
        return "Order Client Down";

    };

}
