package com.broker.repository;
import com.broker.entity.PaymentRetryJob;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentRetryJob, String> {
    List<PaymentRetryJob> findByStatus(String status);
}
