package com.broker.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment_retry_jobs")
public class PaymentRetryJob extends BaseRetryJob {}
