package com.group.mis_servicios.service.mappers;

import com.group.mis_servicios.view.dto.CustomerDTO;
import com.group.mis_servicios.view.dto.ProviderDTO;
import com.group.mis_servicios.view.dto.RegisterDTO;

public class AuthMapper {
    public static RegisterDTO toRegisterDTO(CustomerDTO customer) {
        RegisterDTO registerDTO = new RegisterDTO();

        registerDTO.setUsername(customer.getUsername());
        registerDTO.setPassword(customer.getPassword());
        registerDTO.setFirstName(customer.getFirstName());
        registerDTO.setLastName(customer.getLastName());
        registerDTO.setEmail(customer.getEmail());
        registerDTO.setAddress(customer.getAddress());
        registerDTO.setPhoneNumber(customer.getPhoneNumber());

        return registerDTO;
    }

    public static RegisterDTO toRegisterDTO(ProviderDTO provider) {
        RegisterDTO registerDTO = new RegisterDTO();

        registerDTO.setUsername(provider.getUsername());
        registerDTO.setPassword(provider.getPassword());
        registerDTO.setFirstName(provider.getFirstName());
        registerDTO.setLastName(provider.getLastName());
        registerDTO.setEmail(provider.getEmail());
        registerDTO.setAddress(provider.getAddress());
        registerDTO.setPhoneNumber(provider.getPhoneNumber());

        return registerDTO;
    }
}
