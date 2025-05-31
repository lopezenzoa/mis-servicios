package com.group.mis_servicios.controller;

import com.group.mis_servicios.service.CallService;
import com.group.mis_servicios.view.dto.CallDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/calls")
@CrossOrigin("*")
public class CallController {

    @Autowired
    private CallService service;

    @GetMapping(("/"))
    public ResponseEntity<List<CallDTO>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Optional<CallDTO> optionalCall = service.findById(id);
        if (optionalCall.isPresent()) {
            return new ResponseEntity<>(optionalCall.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("The call has no been found with the ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<CallDTO> update(@PathVariable("id") Long id, @RequestBody CallDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody CallDTO dto) {
        service.create(dto);
        return new ResponseEntity<> ("The call has been created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/calls/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("The call has been deleted successfully");
    }

}
