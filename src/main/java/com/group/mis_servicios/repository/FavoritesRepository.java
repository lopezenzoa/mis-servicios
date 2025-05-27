package com.group.mis_servicios.repository;

import com.group.mis_servicios.entity.FavoritesList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepository extends JpaRepository<FavoritesList, Long> {

}
