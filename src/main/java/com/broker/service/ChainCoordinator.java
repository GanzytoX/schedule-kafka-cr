package com.broker.service;

import com.broker.chain.*;
import com.broker.entity.*;
import com.broker.repository.*;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class ChainCoordinator {
    private final StepARetryEndpoint stepA;
    private final StepBEmailNotification stepB;
    private final StepCMongoPersistence stepD; // Es el paso D del diagrama
    
    // Repositorios de Postgres (Paso C)
    private final PaymentRepository paymentRepo;
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    public ChainCoordinator(StepARetryEndpoint stepA, StepBEmailNotification stepB, 
                            StepCMongoPersistence stepD, PaymentRepository paymentRepo,
                            OrderRepository orderRepo, ProductRepository productRepo) {
        this.stepA = stepA;
        this.stepB = stepB;
        this.stepD = stepD;
        this.paymentRepo = paymentRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        
        // Armamos la cadena: A -> B -> D
        this.stepA.setNext(this.stepB);
        this.stepB.setNext(this.stepD);
    }

    public void executeChain(String id, String type, Map<String, Object> data) {
        ChainState state = ChainState.builder()
                .dbId(id)
                .type(type)
                .data(data)
                .build();

        try {
            // Ejecutar la cadena (Pasos A, B y D)
            stepA.handle(state);

            // PASO C (Relacional): Actualizamos el estado en Postgres a SUCCESS
            updateStatusInPostgres(id, type, "SUCCESS");
            System.out.println("[PASO C] Estado actualizado a SUCCESS en PostgreSQL para: " + id);

        } catch (Exception e) {
            updateStatusInPostgres(id, type, "FAILED");
            System.err.println("Error en la cadena: " + e.getMessage());
        }
    }

    private void updateStatusInPostgres(String id, String type, String status) {
        switch (type) {
            case "payment":
                paymentRepo.findById(id).ifPresent(j -> { j.setStatus(status); paymentRepo.save(j); });
                break;
            case "order":
                orderRepo.findById(id).ifPresent(j -> { j.setStatus(status); orderRepo.save(j); });
                break;
            case "product":
                productRepo.findById(id).ifPresent(j -> { j.setStatus(status); productRepo.save(j); });
                break;
        }
    }
}
