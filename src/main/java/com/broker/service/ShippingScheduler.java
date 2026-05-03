package com.broker.service;

import com.broker.entity.Shipping;
import com.broker.repository.ShippingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingScheduler {

    private final ShippingRepository shippingRepository;

    @Scheduled(fixedRate = 10000)
    public void processPendingShippings() {
        System.out.println("[Shipping Scheduler] Buscando envíos PENDIENTES...");
        List<Shipping> pendingShippings = shippingRepository.findByStatus("PENDING_NOTIFICATION");
        
        for (Shipping shipping : pendingShippings) {
            System.out.println("  -> [Shipping Scheduler] Enviar correo confirmando envío de orden para la orden: " + shipping.getOrderId());
            
            // Simular el envío de correo y actualizar el estatus
            shipping.setStatus("NOTIFIED");
            shipping.setUpdatedAt(LocalDateTime.now());
            shippingRepository.save(shipping);
            
            System.out.println("  -> [Shipping Scheduler] Notificación enviada. Estatus actualizado a NOTIFIED.");
        }
    }
}
