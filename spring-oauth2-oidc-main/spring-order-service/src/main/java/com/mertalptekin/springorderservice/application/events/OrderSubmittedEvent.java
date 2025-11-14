package com.mertalptekin.springorderservice.application.events;

// MS arası Veri Entegrasyonu için kullanacağız.
public record OrderSubmittedEvent(String orderCode,String status, Integer quantity, String productName) {
}
