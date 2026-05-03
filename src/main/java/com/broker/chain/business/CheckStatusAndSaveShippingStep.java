package com.broker.chain.business;

import com.broker.entity.Shipping;
import com.broker.repository.ShippingRepository;
import org.springframework.stereotype.Component;

@Component
public class CheckStatusAndSaveShippingStep extends BusinessStep {
    
    private final ShippingRepository shippingRepository;

    public CheckStatusAndSaveShippingStep(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }

    @Override
    public void handle(BusinessContext context) {
        boolean shouldSaveShipping = false;
        String orderId = null;

        if (context.getEventType().equals("order_status_changed_events")) {
            String status = context.getJsonNode().path("status").asText("");
            orderId = context.getJsonNode().path("id").asText("");
            if ("Pagado".equalsIgnoreCase(status) || "PAID".equalsIgnoreCase(status)) {
                shouldSaveShipping = true;
            } else {
                 System.out.println("[Business Step] Estatus de la orden no es Pagado (" + status + "). Fin del flujo.");
            }
        } else if (context.getEventType().equals("payment_received_events")) {
            // Evaluamos si está pagada completamente (asumimos que si llega aquí, el pago fue procesado)
            shouldSaveShipping = true;
            orderId = context.getJsonNode().path("orderId").asText("");
        }

        if (shouldSaveShipping && orderId != null && !orderId.isEmpty()) {
            System.out.println("[Business Step] Condición cumplida. Guardar en tabla de envíos (postgres) para orden: " + orderId);
            Shipping shipping = new Shipping();
            shipping.setOrderId(orderId);
            shippingRepository.save(shipping);
        }

        if (next != null) {
            next.handle(context);
        }
    }
}
