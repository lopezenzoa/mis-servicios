package com.group.mis_servicios.controller;

import com.group.mis_servicios.model.entity.FavoritesList;
import com.group.mis_servicios.view.dto.FavoritesResponseDTO;
import com.group.mis_servicios.view.dto.ProviderToFavoritesDTO;
import com.group.mis_servicios.view.dto.FavoritesDTO;
import com.group.mis_servicios.view.dto.ProviderResponseDTO;
import com.group.mis_servicios.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favorites-lists")
public class FavoritesController {
    @Autowired
    private FavoritesService service;

    @PostMapping("/create")
    public ResponseEntity<?> createFavoritesList(@RequestBody FavoritesDTO dto) {
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
    public ResponseEntity<List<ProviderResponseDTO>> getProviders(@PathVariable Long favoritesListId) {
        List<ProviderResponseDTO> providers = service.getProvidersFromFavoritesList(favoritesListId);
        return ResponseEntity.ok(providers);
    }

    @DeleteMapping("/remove-provider")
    public ResponseEntity<?> removeProvider(@RequestBody ProviderToFavoritesDTO dto) {
        service.removeProviderFromFavorites(dto.getFavoritesListId(), dto.getProviderId());

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The provider has been added to the list successfully!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFavoritesList(@PathVariable Long id) {
        boolean deleted = service.deleteFavoritesList(id);

        if (deleted) {
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(Map.of("message", "The favorites list has been deleted successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Content-Type", "application/json")
                    .body(Map.of("message", "The favorites list has been deleted successfully!"));
        }
    }

}
