package com.group.mis_servicios.service;

import com.group.mis_servicios.dto.FavoritesDTO;
import com.group.mis_servicios.dto.FavoritesResponseDTO;
import com.group.mis_servicios.dto.ProviderResponseDTO;
import com.group.mis_servicios.entity.Cliente;
import com.group.mis_servicios.entity.FavoritesList;
import com.group.mis_servicios.entity.Provider;
import com.group.mis_servicios.repository.ClienteRepository;
import com.group.mis_servicios.repository.FavoritesRepository;
import com.group.mis_servicios.repository.ProviderRepository;
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
    private ClienteRepository clienteRepository;

    @Autowired
    private ProviderRepository providerRepository;

    public FavoritesList createFavoritesList(FavoritesDTO favoritesDTO) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(favoritesDTO.getClientId());
        if (clienteOpt.isEmpty()) {
            throw new RuntimeException("Cliente no encontrado");
        }

        FavoritesList list = new FavoritesList();
        list.setCliente(clienteOpt.get());
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
        dto.setCategoryName(provider.getCategory().getNombre());
        return dto;
    }


    public FavoritesResponseDTO addProviderToFavorites(Long favoritesListId, Long providerId) {
        FavoritesList list = favoritesListRepository.findById(favoritesListId)
                .orElseThrow(() -> new RuntimeException("Lista de favoritos no encontrada"));

        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        if (!list.getProvidersList().contains(provider)) {
            list.getProvidersList().add(provider);
        }

        FavoritesList saved = favoritesListRepository.save(list);

        // Conversión de providers a DTO response
        List<ProviderResponseDTO> providersDto = saved.getProvidersList().stream()
                .map(this::mapToProviderDTO)
                .toList();

        // Se crea y devuelve el DTO de la lista de favoritos
        FavoritesResponseDTO dto = new FavoritesResponseDTO();
        dto.setId(saved.getId());
        dto.setTitle(saved.getTitle());
        dto.setCreationDate(saved.getCreationDate());
        dto.setClientId(saved.getCliente().getId());
        dto.setProviders(providersDto);

        return dto;
    }

    public List<ProviderResponseDTO> getProvidersFromFavoritesList(Long favoritesListId) {
        FavoritesList list = favoritesListRepository.findById(favoritesListId)
                .orElseThrow(() -> new RuntimeException("Lista de favoritos no encontrada"));
        return list.getProvidersList()
                .stream()
                .map(provider -> {
                    ProviderResponseDTO dto = new ProviderResponseDTO();
                    dto.setId(provider.getId());
                    dto.setFirstName(provider.getFirstName());
                    dto.setLastName(provider.getLastName());
                    dto.setEmail(provider.getEmail());
                    dto.setAddress(provider.getAddress());
                    dto.setLicenseNumber(provider.getLicenseNumber());
                    dto.setCategoryName(provider.getCategory().getNombre());
                    return dto;
                }).toList();
    }

    public FavoritesResponseDTO removeProviderFromFavorites(Long favoritesListId, Long providerId) {
        FavoritesList list = favoritesListRepository.findById(favoritesListId)
                .orElseThrow(() -> new RuntimeException("Lista de favoritos no encontrada"));

        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        list.getProvidersList().remove(provider);
        FavoritesList updatedList = favoritesListRepository.save(list);

        FavoritesResponseDTO dto = new FavoritesResponseDTO();
        dto.setId(updatedList.getId());
        dto.setTitle(updatedList.getTitle());
        dto.setCreationDate(updatedList.getCreationDate());
        dto.setClientId(updatedList.getCliente().getId());

        List<ProviderResponseDTO> providerDTOs = updatedList.getProvidersList().stream()
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
