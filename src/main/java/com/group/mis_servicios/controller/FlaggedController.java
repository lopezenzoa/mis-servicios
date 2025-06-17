package com.group.mis_servicios.controller;

import com.group.mis_servicios.service.FlaggedProviderService;
import com.group.mis_servicios.view.dto.FlaggedProviderDTO;
import com.group.mis_servicios.view.dto.FlaggedProviderResponseDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flagged-providers")
@Tag(name = "Reportados", description = "Operaciones relacionadas a los proveedores reportados por el cliente")
public class FlaggedController {
    @Autowired
    private FlaggedProviderService flaggedProviderService;

    @PostMapping("/flag")
    @ApiResponse(responseCode = "200", description = "El proveedor se reporto por incumplimiento")
    public ResponseEntity<String> flagProvider(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "El ID del cliente, del proveedor y la razón del reporte")
            @RequestBody FlaggedProviderDTO dto
    ) {
        flaggedProviderService.flagProvider(dto);
        return ResponseEntity.ok("Provider flagged for incompliace.");
    }

    @GetMapping("/history/{providerId}")
    @ApiResponse(responseCode = "200", description = "Obtiene el historial del proveedor")
    public ResponseEntity<List<FlaggedProviderResponseDTO>> getFlagHistory(@PathVariable Long providerId) {
        return ResponseEntity.ok(flaggedProviderService.getProviderFlags(providerId));
    }

    @GetMapping("/count/{providerId}")
    @ApiResponse(responseCode = "200", description = "Obtiene la cantidad de reportes del proveedor")
    public ResponseEntity<Long> getFlagCount(@PathVariable Long providerId) {
        return ResponseEntity.ok(flaggedProviderService.getFlagCount(providerId));
    }



}
