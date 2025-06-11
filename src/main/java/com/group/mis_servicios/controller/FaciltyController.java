package com.group.mis_servicios.controller;



import com.group.mis_servicios.model.entity.Facility;
import com.group.mis_servicios.service.FacilityService;
import com.group.mis_servicios.view.dto.FacilityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facilities")
@CrossOrigin("*")
public class FaciltyController {

    @Autowired
    private FacilityService service;

    @PostMapping("/{id}")
    public ResponseEntity<?> create(@RequestBody FacilityDTO facility) {
        return new ResponseEntity<>(service.create(facility), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<FacilityDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody FacilityDTO facility) {
        return ResponseEntity.ok(service.update(id, facility));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
