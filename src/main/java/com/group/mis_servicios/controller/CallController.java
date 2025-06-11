package com.group.mis_servicios.controller;

import com.group.mis_servicios.service.CallService;
import com.group.mis_servicios.view.dto.CallDTO;
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
public class CallController {

    @Autowired
    private CallService service;

    @GetMapping("/")
    public ResponseEntity<List<CallDTO>> listAll() {
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
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
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CallDTO dto) {
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
    public ResponseEntity<?> create(@Valid @RequestBody CallDTO dto) {
        Optional<CallDTO> optionalCallDTO = service.create(dto);

        if (optionalCallDTO.isPresent())
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(Map.of("message", "The call was successfully updated"));

        return  ResponseEntity.status(400)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "Oops :( There was an error during creating the call"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (service.delete(id))
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(Map.of("message", "The call was successfully deleted"));

        return ResponseEntity.status(404)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "Oops :( The call with the ID of: " + id + " was not found"));
    }

}
