package com.group.mis_servicios.controller;

import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.service.ProviderService;
import com.group.mis_servicios.view.dto.ProviderDTO;
import com.group.mis_servicios.view.dto.ProviderResponseDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/providers")
@CrossOrigin("*")
@Tag(name = "Proveedores", description = "Operaciones relacionadas con los proveedores")
public class ProviderController {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ProviderService service;
    @Autowired
    private ProviderRepository repository;

    private String whatsappNumber;

    @GetMapping("/")
    @ApiResponse(responseCode = "200", description = "Obtiene todos los proveedores")
    public ResponseEntity<List<ProviderResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllResponse()); // ← debe devolver ProviderResponseDTO
    }


    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Proveedor encontrado")
    @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<ProviderResponseDTO> providerDTO = service.getById(id);

        if (providerDTO.isPresent())
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(providerDTO.get());

        return ResponseEntity.status(404)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The provider hasn't been found"));
    }

    @GetMapping("/license/{licenseNumber}")
    @ApiResponse(responseCode = "200", description = "Obtiene un proveedor dado su numero de licencia profesional")
    @ApiResponse(responseCode = "404", description = "Numero de licencia no encontrada")
    public ResponseEntity<?> filterByLicenseNumber(@PathVariable String licenseNumber) {
        Optional<ProviderDTO> providerDTO = service.filterByLicenseNumber(licenseNumber);

        if (providerDTO.isPresent())
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(providerDTO.get());

        return ResponseEntity.status(404)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The provider hasn't been found"));
    }

    @PutMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Proveedor actualizado")
    @ApiResponse(responseCode = "400", description = "El proveedor no se pudo actualizar")
    public ResponseEntity<?> update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cuerpo del prestador a actualizar")
            @PathVariable Long id,
            @RequestBody ProviderDTO dto
    ) {
        Optional<ProviderResponseDTO> providerDTO = service.update(id, dto);

        if (providerDTO.isPresent())
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(Map.of("message", "The provider has been updated successfully!"));

        return ResponseEntity.status(404)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The provider hasn't been found"));
    }

    @PostMapping("/register")
    @ApiResponse(responseCode = "200", description = "Proveedor creado")
    @ApiResponse(responseCode = "400", description = "El proveedor no se pudo crear")
    public ResponseEntity<?> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cuerpo del proveedor a crear")
            @RequestBody ProviderDTO dto
    ) {
        service.create(dto);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The provider has been registered successfully!"));
    }

//    @GetMapping("/facility/{facilityName}")
//    public ResponseEntity<?> getByFacility(@PathVariable String facilityName) {
//        List<ProviderDTO> providerDTOS = service.filterByFacility(facilityName);
//
//        return ResponseEntity.ok()
//                .header("Content-Type", "application/json")
//                .body(providerDTOS);
//    }

//    @GetMapping("/search")
//    public ResponseEntity<List<ProviderResponseDTO>> buscar(
//            @RequestParam(required = false) String firstName,
//            @RequestParam(required = false) String lastName,
//            @RequestParam(required = false) String email,
//            @RequestParam(required = false) String licenseNumber
//    ) {
//        return ResponseEntity.ok(service.filterByCriterios(firstName, lastName, email, licenseNumber));
//    }

//    @PostMapping("/add-facility")
//    public ResponseEntity<?> addFacilityToProvider(@RequestBody FacilityToProviderDTO dto) {
//        boolean added = service.addFacility(dto.getProviderId(), dto.getFacilityId());
//
//        if (added)
//            return ResponseEntity.ok()
//                    .header("Content-Type", "application/json")
//                    .body(Map.of("message", "The facility has been added to the provider successfully!"));
//
//        return ResponseEntity.status(404)
//                .header("Content-Type", "application/json")
//                .body(Map.of("message", "The provider hasn't been found"));
//    }


//    @GetMapping("/page")
//    public ResponseEntity<Page<ProviderResponseDTO>> listAllPaginated(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        Pageable pageable = PageRequest.of(page, size);
//        return ResponseEntity.ok(service.listPage(pageable));
//    }

    // manejo de excecpciones
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "The parameter '" + ex.getName() + "' must be a valid number (Long).";
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
