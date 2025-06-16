package com.group.mis_servicios.controller;

import com.group.mis_servicios.model.entity.Shift;
import com.group.mis_servicios.service.ProviderService;
import com.group.mis_servicios.service.ShiftService;
import com.group.mis_servicios.view.dto.CustomerResponseDTO;
import com.group.mis_servicios.view.dto.ProviderDTO;
import com.group.mis_servicios.view.dto.ProviderResponseDTO;
import com.group.mis_servicios.view.dto.ShiftDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/shifts")
@CrossOrigin("*")
@Tag(name = "Turnos", description = "Operaciones relacionadas con los turnos del prestador")
public class ShiftController {
    @Autowired
    private ShiftService service;
    @Autowired
    private ProviderService providerService;

    @GetMapping("/")
    @ApiResponse(responseCode = "200", description = "Obtiene todos los turnos")
    public ResponseEntity<List<ShiftDTO>> getAll() {
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(service.getAll());
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Turno encontrado")
    @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<ShiftDTO> shiftOptional = service.getById(id);

        if (shiftOptional.isPresent())
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(shiftOptional.get());
        else {
            return ResponseEntity.status(404)
                    .header("Content-Type", "application/json")
                    .body(Map.of("message", "The provider hasn't been found"));
        }
    }

//    @GetMapping("/availables")
//    @ApiResponse(responseCode = "200", description = "Obtiene todos los turnos disponibles")
//    public ResponseEntity<List<ShiftDTO>> getAvailables() {
//        return ResponseEntity.ok()
//                .header("Content-Type", "application/json")
//                .body(service.getAvailables());
//    }

    @PostMapping("/create")
    @ApiResponse(responseCode = "200", description = "Turno creado")
    @ApiResponse(responseCode = "404", description = "El proveedor no se pudo encontrar")
    public ResponseEntity<?> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cuerpo del turno a crear")
            @RequestBody ShiftDTO shift
    ) {
        Optional<ShiftDTO> shiftOptional = service.create(shift);

        if (shiftOptional.isPresent())
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(Map.of("message", "The shift has been registered successfully!"));
        else {
            return ResponseEntity.status(404)
                    .header("Content-Type", "application/json")
                    .body(Map.of("message", "The provider hasn't been found"));
        }
    }

    @PutMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Turno actualizado")
    @ApiResponse(responseCode = "404", description = "El proveedor no se encontró")
    public ResponseEntity<?> update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cuerpo del turno a actualizar")
            @PathVariable Long id,
            @RequestBody ShiftDTO updated
    ) {
        Optional<ShiftDTO> customerOptional = service.update(id, updated);

        if (customerOptional.isPresent())
            return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
        else {
            return new ResponseEntity<>("The shift has no been found with the ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Proveedor creado")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>("The shift is not more in your list", HttpStatus.OK);
    }

    @GetMapping("/availables/{providerId}")
    @ApiResponse(responseCode = "200", description = "Obtiene todos los turnos disponibles de un proveedor")
    @ApiResponse(responseCode = "404", description = "El proveedor no se encontró")
    public ResponseEntity<?> getAvailableByProvider(@PathVariable Long providerId) {
        Optional<ProviderResponseDTO> providerResponseDTO = providerService.getById(providerId);

        if (providerResponseDTO.isPresent())
            return new ResponseEntity<>(service.getAvailableByProvider(providerId), HttpStatus.OK);
        else
            return new ResponseEntity<>("The provider has no been found with the ID: " + providerId, HttpStatus.NOT_FOUND);
    }

    /*
    @PutMapping("/reserve/{id}")
    public ResponseEntity<Shift> reserveShift(@PathVariable Integer id) {
        Shift shift = service.getById(id);
        if (!shift.isAvailable()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // turno ya reservado
        }
        shift.setAvailable(false);
        return new ResponseEntity<>(service.update(id, shift), HttpStatus.OK);
    }

     */
}
