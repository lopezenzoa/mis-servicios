package com.group.mis_servicios.controller;

import com.group.mis_servicios.service.CallService;
import com.group.mis_servicios.view.dto.CallDTO;
import com.group.mis_servicios.view.dto.CallResponseDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/calls")
@CrossOrigin("*")
@Tag(name = "Contrataciones", description = "Operaciones relacionadas a las contrataciones del cliente")
public class CallController {
    @Autowired
    private CallService service;

    @GetMapping("/")
    @ApiResponse(responseCode = "200", description = "Obtiene todas las contrataciones")
    public ResponseEntity<List<CallResponseDTO>> getAll() {
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(service.getAll());
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Contratacion encontrada")
    @ApiResponse(responseCode = "404", description = "Contratacion no encontrada")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<CallDTO> optionalCall = service.getById(id);

        if (optionalCall.isPresent()) {
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(optionalCall.get());
        }

        return ResponseEntity.status(404)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The call has no been found with the ID: " + id));
    }

    @PutMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Contratacion actualizada")
    @ApiResponse(responseCode = "400", description = "La Contratación no se pudo actualizar")
    public ResponseEntity<?> update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cuerpo de la contratacion a actualizar")
            @PathVariable Long id,
            @Valid @RequestBody CallDTO dto
    ) {
        Optional<CallDTO> optionalCallDTO = service.update(id, dto);

        if (optionalCallDTO.isPresent())
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(Map.of("message", "The call with the ID of: " + id + " was successfully updated"));

        return ResponseEntity.status(400)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "Oops :( There was an error during updating the call"));
    }

    @PostMapping("/create")
    @ApiResponse(responseCode = "200", description = "Contratacion creada")
    @ApiResponse(responseCode = "400", description = "La Contratación no se pudo crear")
    public ResponseEntity<?> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cuerpo de la contratacion a crear")
            @Valid @RequestBody CallDTO dto
    ) {
        Optional<CallDTO> optionalCallDTO = service.create(dto);

        if (optionalCallDTO.isPresent())
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(Map.of("message", "The call was successfully created"));

        return  ResponseEntity.status(400)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "Oops :( There was an error during creating the call"));
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Contratacion eliminada")
    @ApiResponse(responseCode = "404", description = "La contratacion no se encontró")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (service.delete(id))
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(Map.of("message", "The call was successfully deleted"));

        return ResponseEntity.status(404)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "Oops :( The call with the ID of: " + id + " was not found"));
    }

}
