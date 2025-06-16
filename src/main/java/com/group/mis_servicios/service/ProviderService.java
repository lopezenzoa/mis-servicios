package com.group.mis_servicios.service;

import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.repository.CredentialsRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.service.mappers.ProviderMapper;
import com.group.mis_servicios.service.validators.ProviderValidator;
import com.group.mis_servicios.view.dto.ProviderDTO;
import com.group.mis_servicios.view.dto.ProviderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderService implements I_Service<ProviderDTO> {
    @Autowired
    private ProviderRepository repository;
    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public List<ProviderResponseDTO> getAll() {
        List<ProviderResponseDTO> providers = new ArrayList<>();

        repository.findAll()
                .forEach(p -> providers.add(ProviderMapper.toResponeDTO(p)));

        return providers;
    }

    @Override
    public Optional<ProviderResponseDTO> getById(Long id) {
        Optional<Provider> provider = repository.findById(id);

        return provider.map(ProviderMapper::toResponeDTO);
    }

    @Override
    public Optional<ProviderResponseDTO> create(ProviderDTO dto) {
       boolean isValid = ProviderValidator.checkValidity(dto, repository);

        if (!isValid)
            return Optional.empty();

        Provider provider = repository.save(ProviderMapper.toProvider(dto, encoder));

        return Optional.of(ProviderMapper.toResponeDTO(provider));
    }

    @Override
    public Optional<ProviderResponseDTO> update(Long id, ProviderDTO updated) {
        Optional<Provider> providerOptional = repository.findById(id);

        if (providerOptional.isPresent() && ProviderValidator.checkValidity(updated, repository)) {
            Provider saved = repository.save(ProviderMapper.toProvider(updated, encoder));

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
    public List<ProviderResponseDTO> getAllResponse() {
        return repository.findAll().stream().map(provider -> {

            ProviderResponseDTO dto = new ProviderResponseDTO();
            dto.setId(provider.getId());
            dto.setFirstName(provider.getFirstName());
            dto.setLastName(provider.getLastName());
            dto.setFacility(provider.getFacility());
            dto.setAddress(provider.getAddress());
            return dto;
        }).toList();
    }


//    public List<ProviderDTO> filterByFacility(String facilityName) {
//        List<ProviderDTO> providerDTOs = new ArrayList<>();
//        Optional<Facility> facilityOptional = facilityRepository.findByName(facilityName);
//
//        if (facilityOptional.isPresent()) {
//            List<Provider> providers = /*repository.findAll()
//                    .stream()
//                    .filter(p -> p.getFacility() != null)
//                    .filter(p -> p.getFacility().equals(facilityOptional.get()))
//                    .toList() */ new ArrayList<>();
//
//            providers.forEach(provider -> {
//                providerDTOs.add(ProviderMapper.toDTO(provider));
//            });
//        }
//
//        return providerDTOs;
//    }
//
//    public boolean addFacility(Long id, Long facilityId) {
//        Optional<Facility> facilityOptional = facilityRepository.findById(facilityId);
//        Optional<Provider> providerOptional = repository.findById(id);
//
//        if (facilityOptional.isPresent() && providerOptional.isPresent()) {
//            Provider provider = providerOptional.get();
//
//            // provider.setFacility(facilityOptional.get());
//
//            return true;
//        }
//
//        return false;
//    }

    public List<ProviderResponseDTO> filterByCriterios(String firstName, String lastName, String email, String licenseNumber) {
        List<Provider> providers = repository.findByCriterios(firstName, lastName, email, licenseNumber);
        return providers.stream().map(ProviderMapper::toResponeDTO).toList();
    }


    public Page<ProviderResponseDTO> listPage(Pageable pageable) {
        return repository.findAll(pageable)
                .map(ProviderMapper::toResponeDTO);
    }

    public Optional<Provider> getByEmail(String email) {
        return repository.findByEmail(email);
    }
}
