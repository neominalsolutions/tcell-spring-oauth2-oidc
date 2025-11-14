package com.mertalptekin.springorderservice.infra.repository;

import com.mertalptekin.springorderservice.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order,Integer> {
}
