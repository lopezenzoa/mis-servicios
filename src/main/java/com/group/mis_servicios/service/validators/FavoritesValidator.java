package com.group.mis_servicios.service.validators;

import com.group.mis_servicios.model.repository.FavoritesRepository;
import com.group.mis_servicios.view.dto.FavoritesDTO;

public class FavoritesValidator {
    private static FavoritesRepository favoritesRepository;

    public static boolean checkValidity(FavoritesDTO dto) {
        // checking that the title is unique for the customer's list
        boolean isTitleUnique = favoritesRepository.findAll()
                .stream()
                .filter(l -> l.getCustomer().getId().equals(dto.getCustomerId()))
                .anyMatch(l -> l.getTitle().equals(dto.getTitle()));


        return !dto.getTitle().isBlank() && isTitleUnique;
    }
}
