package com.group.mis_servicios.controller;



import com.group.mis_servicios.model.entity.Facility;
import com.group.mis_servicios.service.FacilityService;
import com.group.mis_servicios.view.dto.CustomerDTO;
import com.group.mis_servicios.view.dto.FacilityDTO;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/facilities")
@CrossOrigin("*")
public class FaciltyController {

    @Autowired
    private FacilityService service;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody FacilityDTO dto) {
        service.create(dto);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The facility has been registered successfully!"));
    }

    @GetMapping("/")
    public ResponseEntity<List<FacilityDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<FacilityDTO> facilityDTO = service.getById(id);

        if (facilityDTO.isPresent())
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(facilityDTO);

        return ResponseEntity.status(404)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The facility hasn't been found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody FacilityDTO dto) {
        Optional<FacilityDTO> facilityDTO = service.update(id, dto);

        if (facilityDTO.isPresent())
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(Map.of("message", "The facility has been updated successfully!"));

        return ResponseEntity.status(404)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The facility hasn't been found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);

        if (deleted)
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(Map.of("message", "The facility has been deleted successfully!"));

        return ResponseEntity.status(404)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The facility hasn't been found"));
    }

}
