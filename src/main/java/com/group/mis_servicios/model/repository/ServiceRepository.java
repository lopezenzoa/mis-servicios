package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
}
