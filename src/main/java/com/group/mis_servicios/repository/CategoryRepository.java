package com.group.mis_servicios.repository;


import com.group.mis_servicios.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
