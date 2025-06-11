package com.group.mis_servicios.service.mappers;

import com.group.mis_servicios.view.dto.CustomerDTO;
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
}
