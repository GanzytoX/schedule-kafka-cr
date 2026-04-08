package com.broker.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class BaseRetryJob {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(columnDefinition = "TEXT")
    private String data;
    private String status = "PENDING";
    @Column(columnDefinition = "TEXT")
    private String sendEmail = "";
    @Column(columnDefinition = "TEXT")
    private String updateRetryJobs = "";
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
