package com.group.mis_servicios.controller;

import com.group.mis_servicios.model.entity.Shift;
import com.group.mis_servicios.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shifts")
@CrossOrigin("*")
public class ShiftController {
    @Autowired
    private ShiftService service;

    @GetMapping("/")
    public ResponseEntity<List<Shift>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shift> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @GetMapping("/availables")
    public ResponseEntity<List<Shift>> getAvailables() {
        return new ResponseEntity<>(service.getAvailables(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Shift> create(@RequestBody Shift shift) {
        return new ResponseEntity<>(service.create(shift), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shift> update(@PathVariable Integer id, @RequestBody Shift updated) {
        return new ResponseEntity<>(service.update(id, updated), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return new ResponseEntity<>("The shift is not more in your list", HttpStatus.OK);
    }

    @GetMapping("/availables/provider/{providerId}")
    public ResponseEntity<List<Shift>> getAvailableByProvider(@PathVariable Integer providerId) {
        return new ResponseEntity<>(service.getAvailableByProvider(providerId), HttpStatus.OK);
    }

    @PutMapping("/reserve/{id}")
    public ResponseEntity<Shift> reserveShift(@PathVariable Integer id) {
        Shift shift = service.getById(id);
        if (!shift.isAvailable()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // turno ya reservado
        }
        shift.setAvailable(false);
        return new ResponseEntity<>(service.update(id, shift), HttpStatus.OK);
    }


}
