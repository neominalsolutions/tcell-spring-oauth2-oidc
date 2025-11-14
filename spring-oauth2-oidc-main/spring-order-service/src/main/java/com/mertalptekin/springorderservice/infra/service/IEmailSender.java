package com.mertalptekin.springorderservice.infra.service;

// Sipariş Onaylanınca Mail atma
public interface IEmailSender {
    void  send(String to,String message);
}
