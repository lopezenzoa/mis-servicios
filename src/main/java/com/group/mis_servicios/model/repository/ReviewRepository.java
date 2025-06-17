package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCustomerId(Long customerId);
    List<Review> findByProviderId(Long providerId);
}
