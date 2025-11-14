package com.mertalptekin.springorderservice.application.events;

// Hangi product Reserve edildi ?
public record StockReservedEvent(String code,String productName) {
}
