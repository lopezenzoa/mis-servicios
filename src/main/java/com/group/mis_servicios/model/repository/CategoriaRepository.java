package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}