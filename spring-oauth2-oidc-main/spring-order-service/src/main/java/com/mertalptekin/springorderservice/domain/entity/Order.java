package com.mertalptekin.springorderservice.domain.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String status; // En önemli alan -> Submitted, Failed yada Completed

    @Column(nullable = false,unique = true)
    private String code;

    // Aşağıdaki fieldlar OrderItem Entitysinde olsun

    @Column
    private String productName;

    @Column
    private Integer quantity;
}
