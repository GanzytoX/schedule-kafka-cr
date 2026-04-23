package com.broker.chain;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StepARetryEndpoint extends BaseHandler {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void handle(ChainState state) {
        try {
            String url = state.getRetryUrl();
            System.out.println("[PASO A] Reintentando endpoint: " + url);
            restTemplate.postForEntity(url, state.getData(), String.class);
            next(state);
        } catch (Exception e) {
            System.err.println("Error en Paso A: " + e.getMessage());
            state.setError(e.getMessage());
            next(state);
        }
    }
}
