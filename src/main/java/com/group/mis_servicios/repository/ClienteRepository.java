package com.group.mis_servicios.repository;

import com.group.mis_servicios.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
