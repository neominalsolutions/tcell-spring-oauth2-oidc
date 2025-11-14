package com.mertalptekin.springproductservice.events;

// Customer -> Supplier güncellemesini uygulamak zorunda.
public record OrderSubmittedEvent(String orderCode,String status,Integer quantity, String productName) {
}
