package com.mertalptekin.springproductservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mertalptekin.springproductservice.events.OrderSubmittedEvent;
import com.mertalptekin.springproductservice.events.OutOfStockEvent;
import com.mertalptekin.springproductservice.events.StockReservedEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ProductStockOutConsumer {
    private final ObjectMapper objectMapper;
    private final StreamBridge streamBridge;

    public ProductStockOutConsumer(ObjectMapper objectMapper, StreamBridge streamBridge){
        this.objectMapper = objectMapper;
        this.streamBridge = streamBridge;
    }


    @Bean
    public Consumer<Message<String>> stockOut(){
        return message -> {

            try {
               OrderSubmittedEvent event=  objectMapper.readValue(message.getPayload(), OrderSubmittedEvent.class);
               System.out.println("Product Repository or Inventory Service  Update Stock" + event);

               // Simüle edelim -> eğer quantity > 100 ise ozaman rejected, completed
                if(event.quantity() > 100) {
                    System.out.println("Stok Yetersiz -> " + event.quantity());
                    // hangi order olduğunu orderCode üzerinden ise takip ediyorum.
                    var payload = new OutOfStockEvent(event.orderCode(),"Stok Yetersiz");
                    streamBridge.send("OutOfStockEvent-out-0",payload);
                } else {
                    System.out.println("Ürün Reserve edildi -> " + event.productName());
                    var payload = new StockReservedEvent(event.orderCode(),event.productName());
                    streamBridge.send("StockReserved-out-0",payload);
                }

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        };
    }
}
