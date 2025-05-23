package com.group.mis_servicios.service;

import com.group.mis_servicios.dto.ClienteDTO;
import com.group.mis_servicios.dto.ProviderDTO;
import com.group.mis_servicios.entity.Cliente;
import com.group.mis_servicios.entity.Credentials;
import com.group.mis_servicios.entity.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.group.mis_servicios.repository.ProviderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {
    @Autowired
    private ProviderRepository providerRepository;


    public List<Provider> listAll(){
       return providerRepository.findAll();
    }

    public Optional<Provider> getById(Long id){
        return providerRepository.findById(id);
    }

    public Provider filterByLicenseNumber(String licenseNumber){
      return  providerRepository.findByLicenseNumber(licenseNumber).orElse(null);
    }
    public Provider save(Provider provider) {
        return providerRepository.save(provider);
    }


    public ProviderDTO update(Long id, ProviderDTO updated) {
        Optional<Provider> providerOptional = providerRepository.findById(id);

        if (providerOptional.isPresent()) {
            Provider provider = providerOptional.get();

            Provider provider1 = providerRepository.save(mapDtoToProvider(updated));

            provider1.setId(provider1.getId());

            return mapProviderToDto(provider1);
        }

        return new ProviderDTO();
    }

    private ProviderDTO mapProviderToDto(Provider provider) {
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

    private Provider mapDtoToProvider(ProviderDTO dto) {
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
