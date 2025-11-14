package com.mertalptekin.springproductservice.events;

// Neden Rejected ediyorum ?
// Compensate geri alma prosedür
public record OutOfStockEvent(String orderCode, String reason) {
}
