package com.broker.chain.business;

import org.springframework.stereotype.Component;

@Component
public class UpdateInventoryStep extends BusinessStep {

    @Override
    public void handle(BusinessContext context) {
        if (context.getEventType().equals("inventory_update_events")) {
            System.out.println("[Business Step] Actualizar inventario (tabla de productos) de acuerdo a la orden creada/actualizada.");
            // En un entorno real, aquí se llamaría al servicio de productos mediante RestTemplate 
            // o se conectaría directamente a la base de datos para descontar el inventario de los items de la orden.
            System.out.println("  -> Inventario actualizado correctamente.");
        }
        
        if (next != null) {
            next.handle(context);
        }
    }
}
