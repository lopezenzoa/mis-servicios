package com.group.mis_servicios.service.mappers;

import com.group.mis_servicios.model.entity.Credentials;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.enums.Roles;
import com.group.mis_servicios.view.dto.ProviderDTO;
import com.group.mis_servicios.view.dto.ProviderResponseDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class ProviderMapper {
    public static ProviderDTO toDTO(Provider provider) {
        ProviderDTO dto = new ProviderDTO();

        dto.setFirstName(provider.getFirstName());
        dto.setLastName(provider.getLastName());
        dto.setUsername(provider.getCredentials().getUsername());
        dto.setPassword(provider.getCredentials().getPassword());
        dto.setEmail(provider.getEmail());
        dto.setAddress(provider.getAddress());
        dto.setPhoneNumber(provider.getPhoneNumber());
        dto.setLicenseNumber(provider.getLicenseNumber());
        dto.setFacility(provider.getFacility());


        return dto;
    }

    public static Provider toProvider(ProviderDTO dto, BCryptPasswordEncoder encoder) {
        Provider provider = new Provider();
        Credentials credentials = new Credentials();

        credentials.setUsername(dto.getUsername());
        credentials.setRole(Roles.PROVIDER);
        credentials.setPassword(encoder.encode(dto.getPassword()));



        provider.setCredentials(credentials);

        provider.setFirstName(dto.getFirstName());
        provider.setLastName(dto.getLastName());
        provider.setEmail(dto.getEmail());
        provider.setAddress(dto.getAddress());
        provider.setLicenseNumber(dto.getLicenseNumber());
        provider.setPhoneNumber(dto.getPhoneNumber());
        provider.setFacility(dto.getFacility());



        return provider;
    }

    public static ProviderResponseDTO toResponeDTO(Provider provider) {
        ProviderResponseDTO dto = new ProviderResponseDTO();

        dto.setId(provider.getId());
        dto.setFirstName(provider.getFirstName());
        dto.setLastName(provider.getLastName());
        dto.setEmail(provider.getEmail());
        dto.setAddress(provider.getAddress());
        dto.setLicenseNumber(provider.getLicenseNumber());
        dto.setFacility(provider.getFacility());
        dto.setCalls(provider.getCalls() == null ? new ArrayList<>() : CallMapper.toCallDTOList(provider.getCalls()));

        return dto;
    }

    public static List<ProviderResponseDTO> toProviderDTOList(List<Provider> providers) {
        List<ProviderResponseDTO> dtos = new ArrayList<>();

        providers.forEach(provider -> {
            dtos.add(toResponeDTO(provider));
        });

        return dtos;
    }
}
