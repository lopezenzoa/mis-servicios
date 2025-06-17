package com.group.mis_servicios.controller;

import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.service.ProviderService;
import com.group.mis_servicios.service.ShiftService;
import com.group.mis_servicios.view.dto.ProviderResponseDTO;
import com.group.mis_servicios.view.dto.ShiftDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/shifts")
@CrossOrigin("*")
@Tag(name = "Turnos", description = "Operaciones relacionadas con los turnos del prestador")
public class ShiftController {
    @Autowired
    private ProviderRepository providerRepo;

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

    @PostMapping("/reservar/{id}")
    public ResponseEntity<ShiftDTO> reservarTurno(@PathVariable Long id) {
        Optional<ShiftDTO> reservado = service.reserveShift(id);
        return reservado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
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
                    .body(Map.of("message", "The shift hasn't been found"));
        }
    }

//    @GetMapping("/availables")
//    @ApiResponse(responseCode = "200", description = "Obtiene todos los turnos disponibles")
//    public ResponseEntity<List<ShiftDTO>> getAvailables() {
//        return ResponseEntity.ok()
//                .header("Content-Type", "application/json")
//                .body(service.getAvailables());
//    }

    @PostMapping("/create-multiple")
    @ApiResponse(responseCode = "200", description = "Turno creado")
    @ApiResponse(responseCode = "404", description = "El proveedor no se pudo encontrar")
    public ResponseEntity<?> createMultiple(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cuerpo del turno a crear")
            @RequestBody List<ShiftDTO> shifts
    ) {
        List<ShiftDTO> registrados = service.createMultiple(shifts);

        if (!registrados.isEmpty())
            return ResponseEntity.ok(Map.of("message", "The shifts were added successfully"));
        else
            return ResponseEntity.status(400).body(Map.of("message", "No se pudieron registrar los turnos"));
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
    @ApiResponse(responseCode = "200", description = "Turno eliminado")
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

    @GetMapping("/all/{providerId}")
    public ResponseEntity<List<ShiftDTO>> getAllByProvider(@PathVariable Long providerId) {
        return ResponseEntity.ok(service.getAllByProvider(providerId));
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
    @GetMapping("/mis-turnos")
    public ResponseEntity<List<ShiftDTO>> getTurnosDelPrestador(Principal principal) {
        String username = principal.getName();
        System.out.println("🔎 Username del token en /mis-turnos: " + username);

        Optional<Provider> providerOpt = providerRepo.findByCredentials_Username(username);

        if (providerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Long providerId = providerOpt.get().getId();
        List<ShiftDTO> turnos = service.getAllByProvider(providerId);
        return ResponseEntity.ok(turnos);
    }
}
