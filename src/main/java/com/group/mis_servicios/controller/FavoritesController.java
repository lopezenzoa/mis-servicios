package com.group.mis_servicios.controller;

import com.group.mis_servicios.view.dto.ProviderToFavoritesDTO;
import com.group.mis_servicios.view.dto.FavoritesDTO;
import com.group.mis_servicios.view.dto.ProviderResponseDTO;
import com.group.mis_servicios.service.FavoritesService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/favorites-lists")
@Tag(name = "Favoritos", description = "Operaciones relacionadas a la lista de favorites del cliente")
public class FavoritesController {
    @Autowired
    private FavoritesService service;

    @PostMapping("/create")
    @ApiResponse(responseCode = "200", description = "Lista de favoritos creada")
    @ApiResponse(responseCode = "400", description = "La lista de favoritos no se pudo crear")
    public ResponseEntity<?> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cuerpo de la lista a crear")
            @RequestBody FavoritesDTO dto
    ) {
        service.create(dto);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The favorites list has been registered successfully!"));
    }

    @PostMapping("/add-provider")
    @ApiResponse(responseCode = "200", description = "El proveedor se agrego con exito a la lista")
    public ResponseEntity<?> addProvider(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "El ID de la lista y del proveedor")
            @RequestBody ProviderToFavoritesDTO dto
    ) {
        service.addProviderToFavorites(dto.getFavoritesListId(), dto.getProviderId());

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The provider has been added to the list successfully!"));
    }

    @GetMapping("/{favoritesListId}/providers")
    @ApiResponse(responseCode = "200", description = "Obtiene todos los proveedores de la lista especificada")
    @ApiResponse(responseCode = "404", description = "La lista especificada no se encontró")
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
    @ApiResponse(responseCode = "200", description = "El proveedor se removio con exito a la lista")
    @ApiResponse(responseCode = "404", description = "La lista especificada no se encontró")
    public ResponseEntity<?> removeProvider(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "El ID de la lista y del proveedor")
            @RequestBody ProviderToFavoritesDTO dto
    ) {
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
    @ApiResponse(responseCode = "200", description = "Lista eliminada")
    @ApiResponse(responseCode = "404", description = "La lista especificada no se encontró")
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
