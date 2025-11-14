package com.mertalptekin.springorderservice.application.events;

// Neden Rejected ediyorum ?
// Compensate geri alma prosedür
public record OutOfStockEvent(String orderCode, String reason) {
}
