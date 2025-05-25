package com.group.mis_servicios.service;

import com.group.mis_servicios.view.dto.ProviderDTO;
import com.group.mis_servicios.model.entity.Credentials;
import com.group.mis_servicios.model.entity.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.group.mis_servicios.model.repository.ProviderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {
    @Autowired
    private ProviderRepository repository;

    public List<ProviderDTO> listAll(){
        List<Provider> providers = repository.findAll();
        List<ProviderDTO> dtos = new ArrayList<>();

        providers.forEach(p -> dtos.add(providerMapper(p)));

        return dtos;
    }

    public ProviderDTO getById(Integer id){
        Optional<Provider> providerOptional = repository.findById(id);

        return providerOptional.map(this::providerMapper).orElseGet(ProviderDTO::new);
    }

    public ProviderDTO filterByLicenseNumber(String licenseNumber){
        Optional<Provider> providerOptional = repository.findByLicenseNumber(licenseNumber);

        return  providerOptional.map(this::providerMapper).orElseGet(ProviderDTO::new);
    }

    public ProviderDTO create(ProviderDTO provider) {
        return providerMapper(repository.save(providerMapper(provider)));
    }


    public ProviderDTO update(Integer id, ProviderDTO updated) {
        Optional<Provider> providerOptional = repository.findById(id);

        if (providerOptional.isPresent()) {
            Provider provider1 = repository.save(providerMapper(updated));

            return providerMapper(provider1);
        }

        return new ProviderDTO();
    }

    private ProviderDTO providerMapper(Provider provider) {
        ProviderDTO dto = new ProviderDTO();

        dto.setFirstName(provider.getFirstName());
        dto.setLastName(provider.getLastName());
        dto.setUsername(provider.getCredentials().getUsername());
        dto.setPassword(provider.getCredentials().getPassword());
        dto.setEmail(provider.getEmail());
        dto.setAddress(provider.getAddress());
        dto.setLicenseNumber(provider.getLicenseNumber());

        return dto;
    }

    private Provider providerMapper(ProviderDTO dto) {
        Provider provider = new Provider();
        Credentials credentials = new Credentials();

        credentials.setUsername(dto.getUsername());
        credentials.setPassword(dto.getPassword());

        provider.setFirstName(dto.getFirstName());
        provider.setLastName(dto.getLastName());
        provider.setCredentials(credentials);
        provider.setEmail(dto.getEmail());
        provider.setAddress(dto.getAddress());
        provider.setLicenseNumber(dto.getLicenseNumber());

        return provider;
    }
}
