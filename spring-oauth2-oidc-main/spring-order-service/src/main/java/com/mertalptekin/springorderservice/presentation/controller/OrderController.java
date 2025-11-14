package com.mertalptekin.springorderservice.presentation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mertalptekin.springorderservice.application.handlers.ISubmitOrderRequestHandler;
import com.mertalptekin.springorderservice.application.requests.SubmitOrderRequest;
import com.mertalptekin.springorderservice.application.responses.SubmitOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private final ISubmitOrderRequestHandler submitOrderRequestHandler;

    public OrderController(ISubmitOrderRequestHandler submitOrderRequestHandler){
        this.submitOrderRequestHandler = submitOrderRequestHandler;
    }

    @PostMapping
    public ResponseEntity<SubmitOrderResponse> sendMessage(@RequestBody SubmitOrderRequest request) throws JsonProcessingException {

        var response = submitOrderRequestHandler.handle(request);
        return  ResponseEntity.ok(response);
    }
}
