package com.broker.service;

import com.broker.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class JobScheduler {
    private final ChainCoordinator coordinator;
    private final PaymentRepository paymentRepo;
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    @Value("${app.retry.endpoint.payment}")
    private String paymentUrl;

    @Value("${app.retry.endpoint.order}")
    private String orderUrl;

    @Value("${app.retry.endpoint.product}")
    private String productUrl;

    public JobScheduler(ChainCoordinator coordinator, PaymentRepository paymentRepo, 
                        OrderRepository orderRepo, ProductRepository productRepo) {
        this.coordinator = coordinator;
        this.paymentRepo = paymentRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    @Scheduled(fixedRate = 10000) // Cada 10 segundos
    public void processPendingJobs() {
        System.out.println("[Scheduler] Buscando trabajos PENDIENTES...");

        // Procesar Pagos
        paymentRepo.findByStatus("PENDING").forEach(job -> {
            coordinator.executeChain(job.getId(), "PAYMENT", job.getData(), paymentUrl, job);
        });

        // Procesar Ordenes
        orderRepo.findByStatus("PENDING").forEach(job -> {
            coordinator.executeChain(job.getId(), "ORDER", job.getData(), orderUrl, job);
        });

        // Procesar Productos
        productRepo.findByStatus("PENDING").forEach(job -> {
            coordinator.executeChain(job.getId(), "PRODUCT", job.getData(), productUrl, job);
        });
    }
}
