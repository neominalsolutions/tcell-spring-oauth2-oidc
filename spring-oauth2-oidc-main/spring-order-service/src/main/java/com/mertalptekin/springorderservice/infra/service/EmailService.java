package com.mertalptekin.springorderservice.infra.service;

import org.springframework.stereotype.Component;

@Component
public class EmailService implements IEmailSender {
    @Override
    public void send(String to, String message) {
        System.out.println("Siparişiniz başarılı: " + message);
    }
}
