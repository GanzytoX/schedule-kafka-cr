package com.broker.service;

import com.broker.entity.*;
import com.broker.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private final PaymentRepository paymentRepo;
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaConsumerService(PaymentRepository paymentRepo, OrderRepository orderRepo, ProductRepository productRepo) {
        this.paymentRepo = paymentRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    @KafkaListener(topics = "payments_retry_jobs", groupId = "broker-message-group")
    public void listenPayments(String message) {
        System.out.println("[Kafka] Pago recibido: " + message);
        PaymentRetryJob job = new PaymentRetryJob();
        job.setData(message);
        paymentRepo.save(job);
    }

    @KafkaListener(topics = "order_retry_jobs", groupId = "broker-message-group")
    public void listenOrders(String message) {
        System.out.println("[Kafka] Orden recibida: " + message);
        OrderRetryJob job = new OrderRetryJob();
        job.setData(message);
        orderRepo.save(job);
    }

    @KafkaListener(topics = "product_retry_jobs", groupId = "broker-message-group")
    public void listenProducts(String message) {
        System.out.println("[Kafka] Producto recibido: " + message);
        ProductRetryJob job = new ProductRetryJob();
        job.setData(message);
        productRepo.save(job);
    }
}
