package com.broker.chain.business;

import lombok.Data;
import com.fasterxml.jackson.databind.JsonNode;

@Data
public class BusinessContext {
    private String eventType;
    private String rawData;
    private JsonNode jsonNode;
}
