package com.mertalptekin.springorderservice.application.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mertalptekin.springorderservice.application.events.OutOfStockEvent;
import com.mertalptekin.springorderservice.application.events.StockReservedEvent;
import com.mertalptekin.springorderservice.domain.entity.Order;
import com.mertalptekin.springorderservice.infra.repository.IOrderRepository;
import com.mertalptekin.springorderservice.infra.service.IEmailSender;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

@Component
public class ProductStockEventConsumer {

    private final IOrderRepository orderRepository;
    private final ObjectMapper objectMapper;
    private final IEmailSender emailSender;

    public ProductStockEventConsumer(IOrderRepository orderRepository, ObjectMapper objectMapper, IEmailSender emailSender){
        this.orderRepository = orderRepository;
        this.objectMapper = objectMapper;
        this.emailSender = emailSender;
    }


    // Compensate adımı
    @Bean
    public Consumer<Message<String>> OutOfStockEvent(){
        return message -> {

            try {
                String payload = message.getPayload();
                OutOfStockEvent event =  objectMapper.readValue(payload, OutOfStockEvent.class);
                System.out.println("OutOfStockEvent "  + payload);

                Optional<Order> order = orderRepository.findByCode(event.orderCode());

                if(order.isPresent()){
                    var entity = order.get();
                    entity.setStatus("outOfStock");
                    orderRepository.save(entity);
                    emailSender.send("test",event.reason());
                }

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        };
    }

    // Success Status
    @Bean
    public Consumer<Message<String>> StockReserved(){
        return message -> {
            try {
                String payload = message.getPayload();
                StockReservedEvent event =  objectMapper.readValue(payload, StockReservedEvent.class);
                System.out.println("StockReserved "  + payload);

                Optional<Order> order = orderRepository.findByCode(event.code());

                if(order.isPresent()){
                    // Stocktan düşülünce reserve edildi.
                    var entity = order.get();
                    entity.setStatus("OrderCompleted");
                    orderRepository.save(entity);
                    emailSender.send("test","Siparişiniz tamamlandı");
                }

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }


        };
    }

}
