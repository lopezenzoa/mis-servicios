package com.group.mis_servicios.service.mappers;

import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.entity.Favorites;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.view.dto.FavoritesDTO;

import java.util.ArrayList;

public class FavoritesMapper {

    public static Favorites toFavoritesList(FavoritesDTO dto, CustomerRepository customerRepository) {
        Customer customer = customerRepository.getReferenceById(dto.getCustomerId());

        Favorites list = new Favorites();

        list.setCustomer(customer);
        list.setTitle(dto.getTitle());
        list.setCreationDate(dto.getCreationDate());

        return list;
    }

    public static FavoritesDTO toDTO(Favorites list) {
        FavoritesDTO dto = new FavoritesDTO();

        if (list != null) {
            dto.setId(list.getId());
            dto.setTitle(list.getTitle());
            dto.setCustomerId(list.getCustomer().getId());
            dto.setCreationDate(list.getCreationDate());
            dto.setProviders(list.getProviders() == null ? new ArrayList<>() : ProviderMapper.toProviderDTOList(list.getProviders()));
        }

        return dto;
    }
}
