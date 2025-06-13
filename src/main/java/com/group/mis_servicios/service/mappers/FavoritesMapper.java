package com.group.mis_servicios.service.mappers;

import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.entity.Favorites;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.view.dto.FavoritesDTO;

public class FavoritesMapper {
    private static CustomerRepository customerRepository;

    public static Favorites toFavoritesList(FavoritesDTO dto) {
        Customer customer = customerRepository.getReferenceById(dto.getCustomerId());

        Favorites list = new Favorites();

        list.setCustomer(customer);
        list.setTitle(dto.getTitle());
        list.setCreationDate(dto.getCreationDate());

        return list;
    }

    public static FavoritesDTO toDTO(Favorites list) {
        FavoritesDTO dto = new FavoritesDTO();

        dto.setTitle(list.getTitle());
        // dto.setCustomerId(list.getOwnerId());
        dto.setCreationDate(list.getCreationDate());

        return dto;
    }
}
