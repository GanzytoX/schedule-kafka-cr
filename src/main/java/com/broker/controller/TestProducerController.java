package com.broker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestProducerController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @PostMapping("/payment")
    public String sendPayment(@RequestBody Map<String, Object> payload) throws Exception {
        addPendingFields(payload);
        kafkaTemplate.send("payments_retry_jobs", objectMapper.writeValueAsString(payload));
        return "Mensaje de PAGO enviado a Kafka correctamente";
    }

    @PostMapping("/order")
    public String sendOrder(@RequestBody Map<String, Object> payload) throws Exception {
        addPendingFields(payload);
        kafkaTemplate.send("order_retry_jobs", objectMapper.writeValueAsString(payload));
        return "Mensaje de ORDEN enviado a Kafka correctamente";
    }

    @PostMapping("/product")
    public String sendProduct(@RequestBody Map<String, Object> payload) throws Exception {
        addPendingFields(payload);
        kafkaTemplate.send("product_retry_jobs", objectMapper.writeValueAsString(payload));
        return "Mensaje de PRODUCTO enviado a Kafka correctamente";
    }

    private void addPendingFields(Map<String, Object> payload) {
        payload.put("sendEmail", Map.of("status", "PENDING", "message", ""));
        payload.put("updateRetryJobs", Map.of("status", "PENDING", "message", ""));
    }
}
