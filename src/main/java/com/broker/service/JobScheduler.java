package com.broker.service;

import com.broker.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class JobScheduler {
    private final ChainCoordinator coordinator;
    private final PaymentRepository paymentRepo;
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();

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
            coordinator.executeChain(job.getId(), "payment", parseData(job.getData()));
        });

        // Procesar Ordenes
        orderRepo.findByStatus("PENDING").forEach(job -> {
            coordinator.executeChain(job.getId(), "order", parseData(job.getData()));
        });

        // Procesar Productos
        productRepo.findByStatus("PENDING").forEach(job -> {
            coordinator.executeChain(job.getId(), "product", parseData(job.getData()));
        });
    }

    private Map<String, Object> parseData(String data) {
        try {
            return objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            return Map.of("raw", data);
        }
    }
}
