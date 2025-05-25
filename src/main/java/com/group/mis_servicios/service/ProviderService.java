package com.group.mis_servicios.service;

import com.group.mis_servicios.model.repository.ServiceRepository;
import com.group.mis_servicios.view.dto.ProviderDTO;
import com.group.mis_servicios.model.entity.Credentials;
import com.group.mis_servicios.model.entity.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.group.mis_servicios.model.repository.ProviderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProviderService {
    @Autowired
    private ProviderRepository repository;
    @Autowired
    private ServiceRepository serviceRepository;

    public List<ProviderDTO> listAll() {
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

    public List<ProviderDTO> filterByServices(String serviceName) {
        List<ProviderDTO> providerDTOs = new ArrayList<>();
        Optional<com.group.mis_servicios.entity.Service> service = serviceRepository.findByName(serviceName);

        if (service.isPresent()) {
            List<Provider> providers = repository.findAll()
                    .stream()
                    .filter(p -> p.getServices().contains(service.get()))
                    .toList();

            providers.forEach(provider -> {
                providerDTOs.add(providerMapper(provider));
            });
        }

        return providerDTOs;
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

    /*
    public Provider crearProvider(ProviderDTO dto) {
        Provider provider = mapDtoToProvider(dto);
        return providerRepository.save(provider);
    }

    public List<ProviderResponseDTO> buscarPorNombreCategoria(String nombreCategoria) {
        return providerRepository.findByCategory_Nombre(nombreCategoria)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ProviderResponseDTO> buscarPorCriterios(String firstName, String lastName, String email, String licenseNumber) {
        List<Provider> providers = providerRepository.findByCriterios(firstName, lastName, email, licenseNumber);
        return providers.stream().map(this::mapToResponse).toList();
    }

    public Page<ProviderResponseDTO> listarPaginado(Pageable pageable) {
        return providerRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    private ProviderResponseDTO mapToResponse(Provider provider) {
        ProviderResponseDTO dto = new ProviderResponseDTO();
        dto.setId(provider.getId());
        dto.setFirstName(provider.getFirstName());
        dto.setLastName(provider.getLastName());
        dto.setEmail(provider.getEmail());
        dto.setAddress(provider.getAddress());
        dto.setLicenseNumber(provider.getLicenseNumber());
        dto.setCategoryName(provider.getCategory() != null ? provider.getCategory().getNombre() : null);
        return dto;
    }

    public ProviderResponseDTO toResponseDTO(Provider provider) {
        return mapToResponse(provider);
    }

     */
}
