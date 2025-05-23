package com.group.mis_servicios.controller;

import com.group.mis_servicios.entity.Shift;
import com.group.mis_servicios.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shifts")
public class ShiftController {
    @Autowired
    private ShiftService shiftService;

    @GetMapping("/")
    public ResponseEntity<List<Shift>> getAll() {
        return new ResponseEntity<>(shiftService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shift> getById(@PathVariable Long id) {
        return new ResponseEntity<>(shiftService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/availables")
    public ResponseEntity<List<Shift>> getAvailables() {
        return new ResponseEntity<>(shiftService.getAvailables(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Shift> create(@RequestBody Shift shift) {
        return new ResponseEntity<>(shiftService.create(shift), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shift> update(@PathVariable Long id, @RequestBody Shift updated) {
        return new ResponseEntity<>(shiftService.update(id, updated), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        shiftService.delete(id);
        return new ResponseEntity<>("El turno ha sido dado de baja!", HttpStatus.OK);
    }
}
