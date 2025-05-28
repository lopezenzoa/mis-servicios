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

@RestController
@RequestMapping("/favorites-lists")
public class FavoritesController {
    @Autowired
    private FavoritesService service;

    @PostMapping("/create")
    public ResponseEntity<FavoritesList> createFavoritesList(@RequestBody FavoritesDTO dto) {
        FavoritesList list = service.create(dto);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/add-provider")
    public ResponseEntity<FavoritesResponseDTO> addProvider(@RequestBody ProviderToFavoritesDTO dto) {
        FavoritesResponseDTO response = service.addProviderToFavorites(dto.getFavoritesListId(), dto.getProviderId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{favoritesListId}/providers")
    public ResponseEntity<List<ProviderResponseDTO>> getProviders(@PathVariable Long favoritesListId) {
        List<ProviderResponseDTO> providers = service.getProvidersFromFavoritesList(favoritesListId);
        return ResponseEntity.ok(providers);
    }

    @DeleteMapping("/remove-provider")
    public ResponseEntity<FavoritesResponseDTO> removeProvider(@RequestBody ProviderToFavoritesDTO dto) {
        FavoritesResponseDTO response = service.removeProviderFromFavorites(dto.getFavoritesListId(), dto.getProviderId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFavoritesList(@PathVariable Long id) {
        boolean deleted = service.deleteFavoritesList(id);
        if (deleted) {
            return ResponseEntity.ok("The Favorites List has been successfully deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favorites List not found");
        }
    }

}
