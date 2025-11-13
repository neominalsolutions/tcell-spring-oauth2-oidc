package com.mertalptekin.springorderservice.controller;

import com.mertalptekin.springorderservice.dtos.GetOrderRequest;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("api/v1")
public class HomeController {

    // Senaryo -> Eureka server üzerinden Discovery Client ile OrderService Instance name göre bulup RestClient ile bir get isteği atmak. Port bilmediğimizden dolayı DicvoveryClient kullanıcaz.
    // Imperative Yöntem


    @GetMapping
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("İstek başarılı");
    }



    @PostMapping
    public String index(@RequestBody GetOrderRequest request) {

        // arka arka yapılan hatalı isteklerde circuit braker devreye girsin test etmek istiyoruz.
        if(request.payload().contains("fail")){
            throw  new RuntimeException("İstek yapılırken hata meydana geldi");
        }

        return  "Request From Home Controller";
    }
}
