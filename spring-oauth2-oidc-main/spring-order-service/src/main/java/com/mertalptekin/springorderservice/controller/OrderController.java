package com.mertalptekin.springorderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mertalptekin.springorderservice.dtos.SubmitOrderRequest;
import com.mertalptekin.springorderservice.events.OrderSubmittedEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private final StreamBridge streamBridge;
    private final ObjectMapper objectMapper;

    public OrderController(StreamBridge streamBridge, ObjectMapper objectMapper){
        this.streamBridge = streamBridge;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody SubmitOrderRequest request) throws JsonProcessingException {

        var event = new OrderSubmittedEvent(request.orderCode(),request.status(),10,"A Product");
        var payload = objectMapper.writeValueAsString(event);

        // status fail olarak iletilirse
        Message<String> message = MessageBuilder.withPayload(payload).setHeader("eventType","orderSubmitted").build();

        // Kafkaya mesajımızı iletiyoruz
        boolean isSend =  streamBridge.send("orderSubmit-out-0",message);

        if(isSend) {
            return ResponseEntity.ok("Mesaj Gönderildi");
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
}
