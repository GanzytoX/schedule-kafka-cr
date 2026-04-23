package com.broker.service;

import com.broker.chain.*;
import com.broker.entity.BaseRetryJob;
import com.broker.repository.OrderRepository;
import com.broker.repository.PaymentRepository;
import com.broker.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class ChainCoordinator {
    private final StepARetryEndpoint stepA;
    private final StepBEmailNotification stepB;
    private final StepDMongoPersistence stepD;
    
    private final PaymentRepository paymentRepo;
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    @PostConstruct
    public void init() {
        // Configuramos la cadena: A -> B -> D
        stepA.setNext(stepB);
        stepB.setNext(stepD);
    }

    public void executeChain(String jobId, String type, String data, String url, BaseRetryJob job) {
        try {
            System.out.println("\n--- Iniciando Cadena de Responsabilidad para " + type + " ---");
            
            ChainState state = new ChainState();
            state.setJobId(jobId);
            state.setJobType(type);
            state.setData(data);
            state.setRetryUrl(url);

            // Disparar la cadena (A -> B -> D)
            stepA.handle(state);

            // PASO C: Actualizar registro completado Postgres
            JpaRepository repository = getRepository(type);
            if (repository != null) {
                updateStatusToSuccess(job, repository);
            }

            System.out.println("--- Cadena completada con éxito ---\n");
        } catch (Exception e) {
            System.err.println("Error fatal en la cadena: " + e.getMessage());
        }
    }

    private JpaRepository getRepository(String type) {
        if ("PAYMENT".equals(type)) return paymentRepo;
        if ("ORDER".equals(type)) return orderRepo;
        if ("PRODUCT".equals(type)) return productRepo;
        return null;
    }

    private void updateStatusToSuccess(BaseRetryJob job, JpaRepository repository) {
        job.setStatus("SUCCESS");
        repository.save(job);
        System.out.println("[PASO C] Estado actualizado a SUCCESS en PostgreSQL para: " + job.getId());
    }
}
