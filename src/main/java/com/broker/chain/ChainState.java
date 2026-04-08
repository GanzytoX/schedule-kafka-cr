package com.broker.chain;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class ChainState {
    private String dbId;
    private String type; // payment, order, product
    private Map<String, Object> data;
    private Object emailResponse;
    private String error;
    private String finalResult;
}
