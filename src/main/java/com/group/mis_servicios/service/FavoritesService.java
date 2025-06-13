package com.group.mis_servicios.service;

import com.group.mis_servicios.service.mappers.FavoritesMapper;
import com.group.mis_servicios.service.mappers.ProviderMapper;
import com.group.mis_servicios.service.validators.FavoritesValidator;
import com.group.mis_servicios.view.dto.FavoritesDTO;
import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.view.dto.ProviderResponseDTO;
import com.group.mis_servicios.model.entity.Favorites;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.model.repository.FavoritesRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritesService implements I_Service<FavoritesDTO> {
    @Autowired
    private FavoritesRepository favoritesListRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Override
    public List<FavoritesDTO> getAll() {
        return null;
    }

    @Override
    public Optional<FavoritesDTO> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<FavoritesDTO> create(FavoritesDTO favoritesDTO) {
        Optional<Customer> customerOpt = customerRepository.findById(favoritesDTO.getCustomerId());

        if (customerOpt.isEmpty() || FavoritesValidator.checkValidity(favoritesDTO))
            return Optional.empty();

        Favorites saved = favoritesListRepository.save(FavoritesMapper.toFavoritesList(favoritesDTO));

        return Optional.of(FavoritesMapper.toDTO(saved));
    }

    @Override
    public Optional<?> update(Long id, FavoritesDTO newType) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    public Optional<FavoritesDTO> addProviderToFavorites(Long favoritesListId, Long providerId) {
        Optional<Favorites> list = favoritesListRepository.findById(favoritesListId);
        Optional<Provider> provider = providerRepository.findById(providerId);

        if (list.isEmpty() || provider.isEmpty())
            return Optional.empty();

        if (!list.get().getProviders().contains(provider.get()))
            list.get().getProviders().add(provider.get());

        Favorites saved = favoritesListRepository.save(list.get());

        // Conversión de providers a DTO response
        List<ProviderResponseDTO> providersDto = saved.getProviders().stream()
                .map(ProviderMapper::toResponeDTO)
                .toList();

        // Se crea y devuelve el DTO de la lista de favoritos
        FavoritesDTO dto = new FavoritesDTO();

        dto.setId(saved.getId());
        dto.setTitle(saved.getTitle());
        dto.setCreationDate(saved.getCreationDate());
        dto.setCustomerId(saved.getCustomer().getId());
        dto.setProviders(providersDto);

        return Optional.of(dto);
    }

    public Optional<List<ProviderResponseDTO>> getProvidersFromFavoritesList(Long favoritesListId) {
        Optional<Favorites> list = favoritesListRepository.findById(favoritesListId);

        return list.map(favoritesList -> favoritesList.getProviders()
                .stream()
                .map(ProviderMapper::toResponeDTO)
                .toList());
    }

    public Optional<FavoritesDTO> removeProviderFromFavorites(Long favoritesListId, Long providerId) {
        Optional<Favorites> list = favoritesListRepository.findById(favoritesListId);
        Optional<Provider> provider = providerRepository.findById(providerId);

        if (list.isEmpty() || provider.isEmpty())
            return Optional.empty();

        list.get().getProviders().remove(provider.get());

        Favorites updatedList = favoritesListRepository.save(list.get());

        FavoritesDTO dto = new FavoritesDTO();

        dto.setId(updatedList.getId());
        dto.setTitle(updatedList.getTitle());
        dto.setCreationDate(updatedList.getCreationDate());
        dto.setCustomerId(updatedList.getCustomer().getId());

        List<ProviderResponseDTO> providerDTOs = updatedList.getProviders().stream()
                .map(ProviderMapper::toResponeDTO)
                .toList();

        dto.setProviders(providerDTOs);

        return Optional.of(dto);
    }

    public boolean deleteFavoritesList(Long id) {
        Optional<Favorites> optionalList = favoritesListRepository.findById(id);

        if (optionalList.isPresent()) {
            favoritesListRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
