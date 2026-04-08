package com.broker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Document(collection = "processed_results")
@Data
@Builder
public class ProcessedResult {
    @Id
    private String id;
    private String originalId;
    private String type;
    private Object data;
    private String result;
    private LocalDateTime processedAt;
}
