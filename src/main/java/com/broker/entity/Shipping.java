package com.broker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "shippings")
@Data
public class Shipping {
    @Id
    private String id = UUID.randomUUID().toString();
    private String orderId;
    private String status = "PENDING_NOTIFICATION";
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
}
