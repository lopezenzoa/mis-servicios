package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.Call;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallRepository extends JpaRepository<Call, Long> {
}
