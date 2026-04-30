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
            
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            headers.set("X-Retry", "true");
            
            org.springframework.http.HttpEntity<String> request = new org.springframework.http.HttpEntity<>(state.getData(), headers);
            
            org.springframework.http.ResponseEntity<String> response = restTemplate.exchange(
                url, org.springframework.http.HttpMethod.POST, request, String.class);
                
            if (response.getStatusCode().is2xxSuccessful()) {
                next(state);
            } else {
                throw new RuntimeException("HTTP request failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error en Paso A: " + e.getMessage());
            state.setError(e.getMessage());
            throw new RuntimeException("Deteniendo cadena, el reintento falló: " + e.getMessage());
        }
    }
}
