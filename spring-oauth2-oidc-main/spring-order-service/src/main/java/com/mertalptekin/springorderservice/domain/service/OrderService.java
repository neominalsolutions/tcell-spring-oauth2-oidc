package com.mertalptekin.springorderservice.domain.service;

import com.mertalptekin.springorderservice.domain.entity.Order;
import com.mertalptekin.springorderservice.infra.repository.IOrderRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

// Her katman port üzerinden interface üzerinden DIP yaparak loose coupled haberleşsin. Hexagonal Mimari

@Service
public class OrderService implements  IOrderService {

    private final IOrderRepository orderRepository;


    public OrderService(IOrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public void save(Order entity) {
        this.orderRepository.save(entity);
        System.out.println("Order Service Order Saved");
    }
}
