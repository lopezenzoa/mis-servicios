package com.group.mis_servicios.controller;

import com.group.mis_servicios.dto.FavoritesResponseDTO;
import com.group.mis_servicios.dto.ProviderToFavoritesDTO;
import com.group.mis_servicios.dto.FavoritesDTO;
import com.group.mis_servicios.dto.ProviderResponseDTO;
import com.group.mis_servicios.entity.FavoritesList;
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
    private FavoritesService favoritesListService;

    @PostMapping("/create")
    public ResponseEntity<FavoritesList> createFavoritesList(@RequestBody FavoritesDTO dto) {
        FavoritesList list = favoritesListService.createFavoritesList(dto);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/add-provider")
    public ResponseEntity<FavoritesResponseDTO> addProvider(@RequestBody ProviderToFavoritesDTO dto) {
        FavoritesResponseDTO response = favoritesListService.addProviderToFavorites(dto.getFavoritesListId(), dto.getProviderId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{favoritesListId}/providers")
    public ResponseEntity<List<ProviderResponseDTO>> getProviders(@PathVariable Long favoritesListId) {
        List<ProviderResponseDTO> providers = favoritesListService.getProvidersFromFavoritesList(favoritesListId);
        return ResponseEntity.ok(providers);
    }

    @DeleteMapping("/remove-provider")
    public ResponseEntity<FavoritesResponseDTO> removeProvider(@RequestBody ProviderToFavoritesDTO dto) {
        FavoritesResponseDTO response = favoritesListService.removeProviderFromFavorites(dto.getFavoritesListId(), dto.getProviderId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFavoritesList(@PathVariable Long id) {
        boolean deleted = favoritesListService.deleteFavoritesList(id);
        if (deleted) {
            return ResponseEntity.ok("Lista de favoritos eliminada.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista no encontrada.");
        }
    }

}
