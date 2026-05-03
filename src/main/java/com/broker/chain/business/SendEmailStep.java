package com.broker.chain.business;

import org.springframework.stereotype.Component;

@Component
public class SendEmailStep extends BusinessStep {
    @Override
    public void handle(BusinessContext context) {
        System.out.println("[Business Step] Enviando correo electrónico...");
        if (context.getEventType().equals("order_status_changed_events")) {
             System.out.println("  -> Correo: Tu orden ha cambiado de estatus.");
        } else if (context.getEventType().equals("payment_received_events")) {
             System.out.println("  -> Correo: Hemos recibido tu pago.");
        }
        
        if (next != null) {
            next.handle(context);
        }
    }
}
