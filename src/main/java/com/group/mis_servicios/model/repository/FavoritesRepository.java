package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.FavoritesList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepository extends JpaRepository<FavoritesList, Long> {

}
