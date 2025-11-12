package com.mertalptekin.springorderservice.controller;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class HomeController {

    // Senaryo -> Eureka server üzerinden Discovery Client ile OrderService Instance name göre bulup RestClient ile bir get isteği atmak. Port bilmediğimizden dolayı DicvoveryClient kullanıcaz.
    // Imperative Yöntem




    @GetMapping
    public String index() {
        return  "Request From Home Controller";
    }
}
