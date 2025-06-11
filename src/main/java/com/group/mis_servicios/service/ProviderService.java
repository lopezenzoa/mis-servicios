package com.group.mis_servicios.service;

import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.entity.Facility;
import com.group.mis_servicios.model.repository.CredentialsRepository;
import com.group.mis_servicios.model.repository.FacilityRepository;
import com.group.mis_servicios.service.mappers.ProviderMapper;
import com.group.mis_servicios.service.validators.ProviderValidator;
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
public class ProviderService implements I_Service<ProviderDTO> {
    @Autowired
    private ProviderRepository repository;
    @Autowired
    private FacilityRepository facilityRepository;
    @Autowired
    private CredentialsRepository credentialsRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public List<ProviderDTO> getAll() {
        List<ProviderDTO> providers = new ArrayList<>();

        repository.findAll()
                .forEach(p -> providers.add(ProviderMapper.toDTO(p)));

        return providers;
    }

    @Override
    public Optional<ProviderDTO> getById(Long id) {
        Optional<Provider> provider = repository.findById(id);

        return provider.map(ProviderMapper::toDTO);
    }

    @Override
    public Optional<ProviderResponseDTO> create(ProviderDTO dto) {
        boolean isValid = ProviderValidator.checkValidity(dto);

        if (!isValid)
            return Optional.empty();

        Provider provider = repository.save(ProviderMapper.toProvider(dto));

        return Optional.of(ProviderMapper.toResponeDTO(provider));
    }

    @Override
    public Optional<ProviderResponseDTO> update(Long id, ProviderDTO updated) {
        Optional<Provider> providerOptional = repository.findById(id);

        if (providerOptional.isPresent() && ProviderValidator.checkValidity(updated)) {
            Provider saved = repository.save(ProviderMapper.toProvider(updated));

            return Optional.of(ProviderMapper.toResponeDTO(saved));
        }

        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }

        return false;
    }

    public Optional<ProviderDTO> filterByLicenseNumber(String licenseNumber){
        Optional<Provider> providerOptional = repository.findByLicenseNumber(licenseNumber);

        return providerOptional.map(ProviderMapper::toDTO);
    }

    public List<ProviderDTO> filterByFacility(String facilityName) {
        List<ProviderDTO> providerDTOs = new ArrayList<>();
        Optional<Facility> facilityOptional = facilityRepository.findByName(facilityName);

        if (facilityOptional.isPresent()) {
            List<Provider> providers = /*repository.findAll()
                    .stream()
                    .filter(p -> p.getFacility() != null)
                    .filter(p -> p.getFacility().equals(facilityOptional.get()))
                    .toList() */ new ArrayList<>();

            providers.forEach(provider -> {
                providerDTOs.add(ProviderMapper.toDTO(provider));
            });
        }

        return providerDTOs;
    }

    public boolean addFacility(Long id, Long facilityId) {
        Optional<Facility> facilityOptional = facilityRepository.findById(facilityId);
        Optional<Provider> providerOptional = repository.findById(id);

        if (facilityOptional.isPresent() && providerOptional.isPresent()) {
            Provider provider = providerOptional.get();

            // provider.setFacility(facilityOptional.get());

            return true;
        }

        return false;
    }

    public List<ProviderResponseDTO> filterByCriterios(String firstName, String lastName, String email, String licenseNumber) {
        List<Provider> providers = repository.findByCriterios(firstName, lastName, email, licenseNumber);
        return providers.stream().map(ProviderMapper::toResponeDTO).toList();
    }


    public Page<ProviderResponseDTO> listPage(Pageable pageable) {
        return repository.findAll(pageable)
                .map(ProviderMapper::toResponeDTO);
    }
}
