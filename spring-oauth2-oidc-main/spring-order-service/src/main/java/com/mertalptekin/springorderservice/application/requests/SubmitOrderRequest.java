package com.mertalptekin.springorderservice.application.requests;

public record SubmitOrderRequest(String orderCode,String productName,Integer quantity) {
}
