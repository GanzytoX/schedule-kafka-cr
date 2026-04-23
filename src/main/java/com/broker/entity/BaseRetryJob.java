package com.broker.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseRetryJob {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String data;
    private String status = "PENDING"; // Establecer valor por defecto a PENDING
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }
    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    // Manual Accessors
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
