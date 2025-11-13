package com.mertalptekin.springorderservice.controller;

import com.mertalptekin.springorderservice.dtos.SubmitOrderRequest;
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

    public OrderController(StreamBridge streamBridge){
        this.streamBridge = streamBridge;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody SubmitOrderRequest request){

        // status fail olarak iletilirse
        Message<String> message = MessageBuilder.withPayload(request.status()).setHeader("eventType","orderSubmitted").build();

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
