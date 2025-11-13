package com.mertalptekin.springorderservice.consumer;


import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class SubmitOrderConsumer {



    // Not: spring.cloud.function.definition=submitOrderEvent
    // function ismi ile eşleşmeli
//    @Bean
//    public Consumer<Message<String>> submitOrderEvent(){
//         return message -> {
//            String payload = message.getPayload();
//            var Headers = message.getHeaders();
//
//            System.out.println("payload"  + payload);
//
//         };
//    }

}
