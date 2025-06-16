package com.group.mis_servicios.service.validators;

import com.group.mis_servicios.model.repository.FavoritesRepository;
import com.group.mis_servicios.view.dto.FavoritesDTO;

public class FavoritesValidator {
    //private static FavoritesRepository favoritesRepository;

    public static boolean checkValidity(FavoritesDTO dto, FavoritesRepository repo) {
        boolean isTitleUnique = repo.findAll()
                .stream()
                .filter(fav -> fav.getCustomer().getId().equals(dto.getCustomerId()))
                .noneMatch(fav -> fav.getTitle().equalsIgnoreCase(dto.getTitle()));

        return !dto.getTitle().isBlank() && isTitleUnique;
    }


}
