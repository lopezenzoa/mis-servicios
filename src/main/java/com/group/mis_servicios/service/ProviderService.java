package com.group.mis_servicios.service;

import com.group.mis_servicios.dto.ClienteDTO;
import com.group.mis_servicios.dto.ProviderDTO;
import com.group.mis_servicios.dto.ProviderResponseDTO;
import com.group.mis_servicios.entity.Category;
import com.group.mis_servicios.entity.Credentials;
import com.group.mis_servicios.entity.Provider;
import com.group.mis_servicios.repository.CategoryRepository;
import com.group.mis_servicios.repository.ProviderRepository;
import com.group.mis_servicios.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.group.mis_servicios.repository.ProviderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProviderService {
    @Autowired
    private ProviderRepository providerRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Provider> listAll() {
        return providerRepository.findAll();
    }

    public List<ProviderResponseDTO> listAllProviders() {
        List<Provider> providers = providerRepository.findAll();
        List<ProviderResponseDTO> providerResponseDTOs = new ArrayList<>();

        providers.forEach(provider -> {
            providerResponseDTOs.add(mapToResponse(provider));
        });
        return providerResponseDTOs;
    }

    public Optional<Provider> getById(Long id) {
        return providerRepository.findById(id);
    }

    public Provider filterByLicenseNumber(String licenseNumber) {
        return providerRepository.findByLicenseNumber(licenseNumber).orElse(null);
    }

    public Provider save(Provider provider) {
        return providerRepository.save(provider);
    }

    public List<ProviderResponseDTO> filterByServices(String serviceName) {
        List<ProviderResponseDTO> providerResponseDTOS = new ArrayList<>();
        Optional<com.group.mis_servicios.entity.Service> service = serviceRepository.findByName(serviceName);

        if (service.isPresent()) {
            List<Provider> providers = providerRepository.findAll()
                    .stream()
                    .filter(p -> p.getServices().contains(service.get()))
                    .toList();

            providers.forEach(provider -> {
                providerResponseDTOS.add(mapToResponse(provider));
            });
        }

        return providerResponseDTOS;
    }

    public ProviderDTO update(Long id, ProviderDTO updated) {
        Optional<Provider> providerOptional = providerRepository.findById(id);

        if (providerOptional.isPresent()) {
            Provider provider = mapDtoToProvider(updated);
            provider.setId(id);
            providerRepository.save(provider);
            return mapProviderToDto(provider);
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
        dto.setCategoryId(provider.getCategory() != null ? provider.getCategory().getId() : null);
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

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            provider.setCategory(category);
        }

        return provider;
    }

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
}
