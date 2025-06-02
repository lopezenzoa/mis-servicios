package com.group.mis_servicios.service;

import com.group.mis_servicios.view.dto.FavoritesDTO;
import com.group.mis_servicios.view.dto.FavoritesResponseDTO;
import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.view.dto.ProviderResponseDTO;
import com.group.mis_servicios.model.entity.FavoritesList;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.model.repository.FavoritesRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FavoritesService {

    @Autowired
    private FavoritesRepository favoritesListRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProviderRepository providerRepository;

    public FavoritesList create(FavoritesDTO favoritesDTO) {
        Optional<Customer> customerOpt = customerRepository.findById(favoritesDTO.getClientId());
        
        if (customerOpt.isEmpty()) {
            throw new RuntimeException("Customer not found!");
        }

        FavoritesList list = new FavoritesList();
        list.setCustomer(customerOpt.get());
        list.setTitle(favoritesDTO.getTitle());
        list.setCreationDate(LocalDateTime.now());

        return favoritesListRepository.save(list);
    }

    private ProviderResponseDTO mapToProviderDTO(Provider provider) {
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


    public FavoritesResponseDTO addProviderToFavorites(Long favoritesListId, Long providerId) {
        FavoritesList list = favoritesListRepository.findById(favoritesListId)
                .orElseThrow(() -> new RuntimeException("Favorties List not found"));

        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        if (!list.getProviders().contains(provider)) {
            list.getProviders().add(provider);
        }

        FavoritesList saved = favoritesListRepository.save(list);

        // Conversión de providers a DTO response
        List<ProviderResponseDTO> providersDto = saved.getProviders().stream()
                .map(this::mapToProviderDTO)
                .toList();

        // Se crea y devuelve el DTO de la lista de favoritos
        FavoritesResponseDTO dto = new FavoritesResponseDTO();
        dto.setId(saved.getId());
        dto.setTitle(saved.getTitle());
        dto.setCreationDate(saved.getCreationDate());
        dto.setClientId(saved.getCustomer().getId());
        dto.setProviders(providersDto);

        return dto;
    }

    public List<ProviderResponseDTO> getProvidersFromFavoritesList(Long favoritesListId) {
        FavoritesList list = favoritesListRepository.findById(favoritesListId)
                .orElseThrow(() -> new RuntimeException("Favorties List not found"));
        return list.getProviders()
                .stream()
                .map(provider -> {
                    ProviderResponseDTO dto = new ProviderResponseDTO();
                    dto.setId(provider.getId());
                    dto.setFirstName(provider.getFirstName());
                    dto.setLastName(provider.getLastName());
                    dto.setEmail(provider.getEmail());
                    dto.setAddress(provider.getAddress());
                    dto.setLicenseNumber(provider.getLicenseNumber());
                    dto.setFacility(provider.getFacility().getName());
                    return dto;
                }).toList();
    }

    public FavoritesResponseDTO removeProviderFromFavorites(Long favoritesListId, Long providerId) {
        FavoritesList list = favoritesListRepository.findById(favoritesListId)
                .orElseThrow(() -> new RuntimeException("Favorties List not found"));

        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        list.getProviders().remove(provider);
        FavoritesList updatedList = favoritesListRepository.save(list);

        FavoritesResponseDTO dto = new FavoritesResponseDTO();
        dto.setId(updatedList.getId());
        dto.setTitle(updatedList.getTitle());
        dto.setCreationDate(updatedList.getCreationDate());
        dto.setClientId(updatedList.getCustomer().getId());

        List<ProviderResponseDTO> providerDTOs = updatedList.getProviders().stream()
                .map(this::mapToProviderDTO)
                .toList();
        dto.setProviders(providerDTOs);

        return dto;
    }

    public boolean deleteFavoritesList(Long id) {
        Optional<FavoritesList> optionalList = favoritesListRepository.findById(id);
        if (optionalList.isPresent()) {
            favoritesListRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
