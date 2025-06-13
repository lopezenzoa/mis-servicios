package com.group.mis_servicios.controller;

import com.group.mis_servicios.view.dto.ProviderToFavoritesDTO;
import com.group.mis_servicios.view.dto.FavoritesDTO;
import com.group.mis_servicios.view.dto.ProviderResponseDTO;
import com.group.mis_servicios.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/favorites-lists")
public class FavoritesController {
    @Autowired
    private FavoritesService service;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody FavoritesDTO dto) {
        service.create(dto);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The favorites list has been registered successfully!"));
    }

    @PostMapping("/add-provider")
    public ResponseEntity<?> addProvider(@RequestBody ProviderToFavoritesDTO dto) {
        service.addProviderToFavorites(dto.getFavoritesListId(), dto.getProviderId());

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The provider has been added to the list successfully!"));
    }

    @GetMapping("/{favoritesListId}/providers")
    public ResponseEntity<?> getProviders(@PathVariable("favoritesListId") Long id) {
        Optional<List<ProviderResponseDTO>> providers = service.getProvidersFromFavoritesList(id);

        if (providers.isPresent())
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(providers.get());

        return ResponseEntity.status(404)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The list with the ID of: " + id + " was not found"));
    }

    @DeleteMapping("/remove-provider")
    public ResponseEntity<?> removeProvider(@RequestBody ProviderToFavoritesDTO dto) {
        Optional<FavoritesDTO> listOptional = service.removeProviderFromFavorites(dto.getFavoritesListId(), dto.getProviderId());

        if (listOptional.isPresent())
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(Map.of("message", "The provider has been added to the list successfully!"));

        return ResponseEntity.status(404)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The provider with the ID of: " + dto.getProviderId() + " was not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = service.deleteFavoritesList(id);

        if (deleted) {
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(Map.of("message", "The favorites list has been deleted successfully!"));
        }

        return ResponseEntity.status(404)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The list with the ID of: " + id + " was not found"));
    }

}
