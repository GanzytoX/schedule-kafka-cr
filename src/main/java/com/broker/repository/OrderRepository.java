package com.broker.repository;
import com.broker.entity.OrderRetryJob;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderRetryJob, String> {
    List<OrderRetryJob> findByStatus(String status);
}
