package com.mertalptekin.springorderservice.application.requests;

public record SubmitOrderRequest(String code, String productName, Integer quantity) {
}
