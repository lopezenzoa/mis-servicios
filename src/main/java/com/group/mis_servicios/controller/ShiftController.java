package com.group.mis_servicios.controller;

import com.group.mis_servicios.model.entity.Shift;
import com.group.mis_servicios.service.ProviderService;
import com.group.mis_servicios.service.ShiftService;
import com.group.mis_servicios.view.dto.CustomerResponseDTO;
import com.group.mis_servicios.view.dto.ProviderResponseDTO;
import com.group.mis_servicios.view.dto.ShiftDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shifts")
@CrossOrigin("*")
public class ShiftController {
    @Autowired
    private ShiftService service;
    @Autowired
    private ProviderService providerService;

    @GetMapping("/")
    public ResponseEntity<List<ShiftDTO>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Optional<ShiftDTO> shiftOptional = service.getById(id);

        if (shiftOptional.isPresent())
            return new ResponseEntity<>(shiftOptional.get(), HttpStatus.OK);
        else {
            return new ResponseEntity<>("The shift has no been found with the ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/availables")
    public ResponseEntity<List<ShiftDTO>> getAvailables() {
        return new ResponseEntity<>(service.getAvailables(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody ShiftDTO shift) {
        service.createShiftForProvider(shift);
        return new ResponseEntity<>("The shift has been created successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Shift updated) {
        Optional<ShiftDTO> customerOptional = service.update(id, updated);

        if (customerOptional.isPresent())
            return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
        else {
            return new ResponseEntity<>("The shift has no been found with the ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return new ResponseEntity<>("The shift is not more in your list", HttpStatus.OK);
    }

    @GetMapping("/availables/{providerId}")
    public ResponseEntity<?> getAvailableByProvider(@PathVariable Integer providerId) {
        Optional<ProviderResponseDTO> providerResponseDTO = providerService.getById(providerId);

        if (providerResponseDTO.isPresent())
            return new ResponseEntity<>(service.getAvailableByProvider(providerId), HttpStatus.OK);
        else
            return new ResponseEntity<>("The shift has no been found with the ID: " + providerId, HttpStatus.NOT_FOUND);
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
