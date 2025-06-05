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

    public Optional<FavoritesDTO> create(FavoritesDTO favoritesDTO) {
        Optional<Customer> customerOpt = customerRepository.findById(favoritesDTO.getCustomerId());
        
        if (customerOpt.isEmpty() || checkValidity(favoritesDTO))
            return Optional.empty();

        FavoritesList saved = favoritesListRepository.save(listMapper(favoritesDTO));

        return Optional.of(listMapper(saved));
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


    public Optional<FavoritesResponseDTO> addProviderToFavorites(Long favoritesListId, Long providerId) {
        Optional<FavoritesList> list = favoritesListRepository.findById(favoritesListId);
        Optional<Provider> provider = providerRepository.findById(providerId);

        if (list.isEmpty() || provider.isEmpty())
            return Optional.empty();

        if (!list.get().getProviders().contains(provider.get()))
            list.get().getProviders().add(provider.get());

        FavoritesList saved = favoritesListRepository.save(list.get());

        // Conversión de providers a DTO response
        List<ProviderResponseDTO> providersDto = saved.getProviders().stream()
                .map(this::mapToProviderDTO)
                .toList();

        // Se crea y devuelve el DTO de la lista de favoritos
        FavoritesResponseDTO dto = new FavoritesResponseDTO();

        dto.setId(saved.getId());
        dto.setTitle(saved.getTitle());
        dto.setCreationDate(saved.getCreationDate());
        dto.setCustomerId(saved.getCustomer().getId());
        dto.setProviders(providersDto);

        return Optional.of(dto);
    }

    public Optional<List<ProviderResponseDTO>> getProvidersFromFavoritesList(Long favoritesListId) {
        Optional<FavoritesList> list = favoritesListRepository.findById(favoritesListId);

        return list.map(favoritesList -> favoritesList.getProviders()
                .stream()
                .map(this::mapToProviderDTO)
                .toList());
    }

    public Optional<FavoritesResponseDTO> removeProviderFromFavorites(Long favoritesListId, Long providerId) {
        Optional<FavoritesList> list = favoritesListRepository.findById(favoritesListId);
        Optional<Provider> provider = providerRepository.findById(providerId);

        if (list.isEmpty() || provider.isEmpty())
            return Optional.empty();

        list.get().getProviders().remove(provider.get());

        FavoritesList updatedList = favoritesListRepository.save(list.get());

        FavoritesResponseDTO dto = new FavoritesResponseDTO();

        dto.setId(updatedList.getId());
        dto.setTitle(updatedList.getTitle());
        dto.setCreationDate(updatedList.getCreationDate());
        dto.setCustomerId(updatedList.getCustomer().getId());

        List<ProviderResponseDTO> providerDTOs = updatedList.getProviders().stream()
                .map(this::mapToProviderDTO)
                .toList();

        dto.setProviders(providerDTOs);

        return Optional.of(dto);
    }

    public boolean deleteFavoritesList(Long id) {
        Optional<FavoritesList> optionalList = favoritesListRepository.findById(id);

        if (optionalList.isPresent()) {
            favoritesListRepository.deleteById(id);
            return true;
        }

        return false;
    }

    private FavoritesList listMapper(FavoritesDTO dto) {
        Customer customer = customerRepository.getReferenceById(dto.getCustomerId());

        FavoritesList list = new FavoritesList();

        list.setCustomer(customer);
        list.setTitle(dto.getTitle());
        list.setCreationDate(dto.getCreationDate());

        return list;
    }

    private FavoritesDTO listMapper(FavoritesList list) {
        FavoritesDTO dto = new FavoritesDTO();

        dto.setTitle(list.getTitle());
        dto.setCustomerId(list.getOwnerId());
        dto.setCreationDate(list.getCreationDate());

        return dto;
    }

    private boolean checkValidity(FavoritesDTO dto) {
        // checking that the title is unique for the customer's list
        boolean isTitleUnique = favoritesListRepository.findAll()
                .stream()
                .filter(l -> l.getCustomer().getId().equals(dto.getCustomerId()))
                .anyMatch(l -> l.getTitle().equals(dto.getTitle()));


        return !dto.getTitle().isBlank() && isTitleUnique;
    }
}
