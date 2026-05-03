package com.broker.repository;

import com.broker.entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShippingRepository extends JpaRepository<Shipping, String> {
    List<Shipping> findByStatus(String status);
}
