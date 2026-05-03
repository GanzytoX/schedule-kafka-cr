package com.broker.chain.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class BusinessChainCoordinator {
    
    private final SendEmailStep sendEmailStep;
    private final CheckStatusAndSaveShippingStep checkStatusAndSaveShippingStep;
    private final UpdateInventoryStep updateInventoryStep;

    @PostConstruct
    public void init() {
        // Configuramos la cadena: SendEmail -> CheckStatusAndSaveShipping -> UpdateInventory
        sendEmailStep.setNext(checkStatusAndSaveShippingStep);
        checkStatusAndSaveShippingStep.setNext(updateInventoryStep);
    }

    public void executeChain(BusinessContext context) {
        System.out.println("\n--- Iniciando Cadena de Responsabilidad de Negocio para: " + context.getEventType() + " ---");
        try {
            sendEmailStep.handle(context);
            System.out.println("--- Cadena de Negocio completada con éxito ---\n");
        } catch (Exception e) {
            System.err.println("Error en la cadena de negocio: " + e.getMessage());
        }
    }
}
