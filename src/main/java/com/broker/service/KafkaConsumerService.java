package com.broker.service;

import com.broker.entity.*;
import com.broker.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

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

    // --- Nuevos listeners para flujos de negocio ---

    @Autowired
    private com.broker.chain.business.BusinessChainCoordinator businessChainCoordinator;

    @KafkaListener(topics = "order_status_changed_events", groupId = "broker-business-group")
    public void listenOrderStatusChanged(String message) {
        System.out.println("[Kafka] Evento order_status_changed_events recibido: " + message);
        executeBusinessChain("order_status_changed_events", message);
    }

    @KafkaListener(topics = "inventory_update_events", groupId = "broker-business-group")
    public void listenInventoryUpdate(String message) {
        System.out.println("[Kafka] Evento inventory_update_events recibido: " + message);
        executeBusinessChain("inventory_update_events", message);
    }

    @KafkaListener(topics = "payment_received_events", groupId = "broker-business-group")
    public void listenPaymentReceived(String message) {
        System.out.println("[Kafka] Evento payment_received_events recibido: " + message);
        executeBusinessChain("payment_received_events", message);
    }

    private void executeBusinessChain(String eventType, String message) {
        try {
            com.broker.chain.business.BusinessContext context = new com.broker.chain.business.BusinessContext();
            context.setEventType(eventType);
            context.setRawData(message);
            context.setJsonNode(objectMapper.readTree(message));
            businessChainCoordinator.executeChain(context);
        } catch (Exception e) {
            System.err.println("Error procesando evento de negocio: " + e.getMessage());
        }
    }
}
