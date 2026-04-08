package com.broker.repository;
import com.broker.entity.ProductRetryJob;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductRetryJob, String> {
    List<ProductRetryJob> findByStatus(String status);
}
