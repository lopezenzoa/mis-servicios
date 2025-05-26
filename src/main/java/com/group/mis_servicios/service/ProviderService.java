package com.group.mis_servicios.service;

import com.group.mis_servicios.model.entity.Facility;
import com.group.mis_servicios.model.repository.FacilityRepository;
import com.group.mis_servicios.view.dto.ProviderDTO;
import com.group.mis_servicios.view.dto.ProviderResponseDTO;
import com.group.mis_servicios.model.entity.Credentials;
import com.group.mis_servicios.model.entity.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.group.mis_servicios.model.repository.ProviderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {
    @Autowired
    private ProviderRepository repository;
    @Autowired
    private FacilityRepository facilityRepository;

    public List<ProviderResponseDTO> listAll() {
        List<Provider> providers = repository.findAll();
        List<ProviderResponseDTO> dtos = new ArrayList<>();

        providers.forEach(p -> dtos.add(providerResponseMapper(p)));

        return dtos;
    }

    public Optional<ProviderResponseDTO> getById(Integer id) {
        return repository.findById(id).map(this::providerResponseMapper);
    }

    public ProviderDTO filterByLicenseNumber(String licenseNumber){
        Optional<Provider> providerOptional = repository.findByLicenseNumber(licenseNumber);

        return  providerOptional.map(this::providerMapper).orElseGet(ProviderDTO::new);
    }

    public ProviderDTO create(ProviderDTO provider) {
        return providerMapper(repository.save(providerMapper(provider)));
    }

    public List<ProviderResponseDTO> filterByFacility(String facilityName) {
        List<ProviderResponseDTO> providerDTOs = new ArrayList<>();
        Optional<Facility> facilityOptional = facilityRepository.findByName(facilityName);

        if (facilityOptional.isPresent()) {
            List<Provider> providers = repository.findAll()
                    .stream()
                    .filter(p -> p.getFacility().equals(facilityOptional.get()))
                    .toList();

            providers.forEach(provider -> {
                providerDTOs.add(providerResponseMapper(provider));
            });
        }

        return providerDTOs;
    }

    public ProviderResponseDTO update(Integer id, ProviderDTO updated) {
        Optional<Provider> providerOptional = repository.findById(id);

        if (providerOptional.isPresent()) {
            Provider provider1 = repository.save(providerMapper(updated));

            return providerResponseMapper(provider1);
        }

        return new ProviderResponseDTO();
    }

    public List<ProviderDTO> getByCriterios(String firstName, String lastName, String email, String licenseNumber) {
        List<Provider> providers = repository.findByCriterios(firstName, lastName, email, licenseNumber);
        return providers.stream().map(this::providerMapper).toList();
    }


    public Page<ProviderDTO> listPage(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::providerMapper);
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
        // dto.setCategoryId(provider.getCategory() != null ? provider.getCategory().getId() : null);
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

        /*
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            provider.setCategory(category);
        }

         */

        return provider;
    }

    private ProviderResponseDTO providerResponseMapper(Provider provider) {
        ProviderResponseDTO dto = new ProviderResponseDTO();

        dto.setId(provider.getId());
        dto.setFirstName(provider.getFirstName());
        dto.setLastName(provider.getLastName());
        dto.setEmail(provider.getEmail());
        dto.setAddress(provider.getAddress());
        dto.setLicenseNumber(provider.getLicenseNumber());
        dto.setFacility(provider.getFacility().getName());

        return dto;
    }
}
