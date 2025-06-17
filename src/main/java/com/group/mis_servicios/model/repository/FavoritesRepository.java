package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
    Optional<Favorites> findByCustomerId(Long customerId); // evita múltiples listas por cliente.

}
