package com.group.mis_servicios.service.mappers;

import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.entity.FavoritesList;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.view.dto.FavoritesDTO;

public class FavoritesMapper {
    private static CustomerRepository customerRepository;

    public static FavoritesList toFavoritesList(FavoritesDTO dto) {
        Customer customer = customerRepository.getReferenceById(dto.getCustomerId());

        FavoritesList list = new FavoritesList();

        list.setCustomer(customer);
        list.setTitle(dto.getTitle());
        list.setCreationDate(dto.getCreationDate());

        return list;
    }

    public static FavoritesDTO toDTO(FavoritesList list) {
        FavoritesDTO dto = new FavoritesDTO();

        dto.setTitle(list.getTitle());
        // dto.setCustomerId(list.getOwnerId());
        dto.setCreationDate(list.getCreationDate());

        return dto;
    }
}
