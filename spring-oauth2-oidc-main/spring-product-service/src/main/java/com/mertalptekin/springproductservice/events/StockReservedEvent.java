package com.mertalptekin.springproductservice.events;

// Hangi product Reserve edildi ?
public record StockReservedEvent(String code,String productName) {
}
