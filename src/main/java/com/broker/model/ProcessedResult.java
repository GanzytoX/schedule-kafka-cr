package com.broker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "processed_results")
public class ProcessedResult {
    @Id
    private String id;
    private String jobId;
    private String jobType;
    private String data;
    private String status;
    private LocalDateTime processedAt;

    // Manual Accessors
    public void setJobId(String jobId) { this.jobId = jobId; }
    public void setJobType(String jobType) { this.jobType = jobType; }
    public void setData(String data) { this.data = data; }
    public void setStatus(String status) { this.status = status; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }
}
