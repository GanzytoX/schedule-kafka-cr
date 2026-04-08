package com.broker.chain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StepARetryEndpoint extends BaseHandler {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${app.retry.endpoint.payment}") private String paymentUrl;
    @Value("${app.retry.endpoint.order}") private String orderUrl;
    @Value("${app.retry.endpoint.product}") private String productUrl;

    @Override
    public void handle(ChainState state) {
        String url = switch (state.getType()) {
            case "payment" -> paymentUrl;
            case "order" -> orderUrl;
            case "product" -> productUrl;
            default -> null;
        };
        try {
            System.out.println("[PASO A] Reintentando endpoint: " + url);
            next(state);
        } catch (Exception e) {
            state.setError("Fallo Paso A: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
