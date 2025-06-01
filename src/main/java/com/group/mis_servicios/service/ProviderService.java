package com.group.mis_servicios.service;

import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.entity.Facility;
import com.group.mis_servicios.model.repository.FacilityRepository;
import com.group.mis_servicios.view.dto.FacilityDTO;
import com.group.mis_servicios.view.dto.ProviderDTO;
import com.group.mis_servicios.view.dto.ProviderResponseDTO;
import com.group.mis_servicios.model.entity.Credentials;
import com.group.mis_servicios.model.entity.Provider;
import org.apache.catalina.valves.JsonErrorReportValve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Autowired
    private BCryptPasswordEncoder encoder;

    public List<ProviderDTO> listAll() {
        List<Provider> providers = repository.findAll();
        List<ProviderDTO> dtos = new ArrayList<>();

        providers.forEach(p -> dtos.add(providerMapper(p)));

        return dtos;
    }

    public Optional<ProviderDTO> getById(Long id) {
        return repository.findById(id).map(this::providerMapper);
    }

    public Optional<ProviderDTO> filterByLicenseNumber(String licenseNumber){
        Optional<Provider> providerOptional = repository.findByLicenseNumber(licenseNumber);

        if (providerOptional.isPresent())
            return providerOptional.map(this::providerMapper);

        return Optional.empty();
    }

    public ProviderDTO create(ProviderDTO provider) {
        return providerMapper(repository.save(providerMapper(provider)));
    }

    public List<ProviderDTO> filterByFacility(String facilityName) {
        List<ProviderDTO> providerDTOs = new ArrayList<>();
        Optional<Facility> facilityOptional = facilityRepository.findByName(facilityName);

        if (facilityOptional.isPresent()) {
            List<Provider> providers = repository.findAll()
                    .stream()
                    .filter(p -> p.getFacility() != null)
                    .filter(p -> p.getFacility().equals(facilityOptional.get()))
                    .toList();

            providers.forEach(provider -> {
                providerDTOs.add(providerMapper(provider));
            });
        }

        return providerDTOs;
    }

    public boolean addFacility(Long id, Long facilityId) {
        Optional<Facility> facilityOptional = facilityRepository.findById(facilityId);
        Optional<Provider> providerOptional = repository.findById(id);

        if (facilityOptional.isPresent() && providerOptional.isPresent()) {
            Provider provider = providerOptional.get();

            provider.setFacility(facilityOptional.get());

            return true;
        }

        return false;
    }

    public Optional<ProviderDTO> update(Long id, ProviderDTO updated) {
        Optional<Provider> providerOptional = repository.findById(id);

        if (providerOptional.isPresent()) {
            Provider provider = providerOptional.get();

            if (
                    updated.getFirstName().isEmpty()
                            || updated.getLastName().isEmpty()
                            || checkValidPhone(provider.getId(), updated.getPhoneNumber())
                            || updated.getAddress().isEmpty()
                            || checkValidEmail(provider.getId(), updated.getEmail())
                            || updated.getUsername().isEmpty()
                            || updated.getPassword().isEmpty()
            ) {
                return Optional.empty();
            } else {
                provider.setFirstName(updated.getFirstName());
                provider.setLastName(updated.getLastName());
                provider.setEmail(updated.getEmail());
                provider.setAddress(updated.getAddress());
                provider.setPhoneNumber(updated.getPhoneNumber());

                provider.setUsername(updated.getUsername());
                provider.setPassword(encoder.encode(updated.getPassword()));
                // credentials.setUser(customerUpdated);

                Provider saved = repository.save(provider);

                return Optional.of(providerMapper(saved));
            }
        } else {
            return Optional.empty();
        }
    }

    public List<ProviderResponseDTO> filterByCriterios(String firstName, String lastName, String email, String licenseNumber) {
        List<Provider> providers = repository.findByCriterios(firstName, lastName, email, licenseNumber);
        return providers.stream().map(this::providerResponseMapper).toList();
    }


    public Page<ProviderResponseDTO> listPage(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::providerResponseMapper);
    }

    private ProviderDTO providerMapper(Provider provider) {
        ProviderDTO dto = new ProviderDTO();

        dto.setFirstName(provider.getFirstName());
        dto.setLastName(provider.getLastName());
        dto.setUsername(provider.getUsername());
        dto.setPassword(provider.getPassword());
        dto.setEmail(provider.getEmail());
        dto.setAddress(provider.getAddress());
        dto.setPhoneNumber(provider.getPhoneNumber());
        dto.setLicenseNumber(provider.getLicenseNumber());
        // dto.setCategoryId(provider.getCategory() != null ? provider.getCategory().getId() : null);
        return dto;
    }

    private Provider providerMapper(ProviderDTO dto) {
        Provider provider = new Provider();

        provider.setUsername(dto.getUsername());
        provider.setPassword(dto.getPassword());

        provider.setFirstName(dto.getFirstName());
        provider.setLastName(dto.getLastName());
        provider.setEmail(dto.getEmail());
        provider.setAddress(dto.getAddress());
        provider.setLicenseNumber(dto.getLicenseNumber());
        provider.setPhoneNumber(dto.getPhoneNumber());

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
        dto.setFacility(provider.getFacility()==null?null:provider.getFacility().getName());

        return dto;
    }

    // These two validation methods are used for updating (that's why I'm requesting the id)
    public boolean checkValidEmail(Long id, String email) {
        return repository.findAll()
                .stream()
                .filter(c -> !c.getId().equals(id))
                .anyMatch(c -> c.getEmail().equals(email));
    }

    public boolean checkValidPhone(Long id, String phone) {
        return repository.findAll()
                .stream()
                .filter(c -> !c.getId().equals(id))
                .anyMatch(c -> c.getPhoneNumber().equals(phone));
    }

    // These two validation methods are used for creating
    public boolean checkValidEmail(String email) {
        return repository.findAll()
                .stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    public boolean checkValidPhone(String phone) {
        return repository.findAll()
                .stream()
                .anyMatch(c -> c.getPhoneNumber().equals(phone));
    }
}
