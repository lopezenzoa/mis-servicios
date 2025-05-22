package com.group.mis_servicios.repository;

import com.group.mis_servicios.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
