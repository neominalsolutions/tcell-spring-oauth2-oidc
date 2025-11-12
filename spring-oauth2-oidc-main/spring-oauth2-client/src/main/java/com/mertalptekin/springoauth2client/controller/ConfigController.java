package com.mertalptekin.springoauth2client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// Not: application.yml dosyasında active.profiles tanımına göre config serverdan env bazlı configürasyon okur. dev -> için spring-oauth2-client-dev.yml, prod için ise spring-oauth2-client-prod.yml dosyasından okur.

// Test için 1.sırada SpringOAuthServerApp ayağa kaldır
// 2.sırada SpringConfigServerApp ayağa kaldır
// 3.sırada SpringOAuthClient2App auağa kaldır.

@RestController
@RequestMapping("api/config")
public class ConfigController {


    @Value("${serviceName}")
    private String serviceName;

    @GetMapping
    public String index(){
        return "serviceName: " + serviceName;
    }


}
