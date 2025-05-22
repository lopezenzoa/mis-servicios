package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ProviderService;
import entity.Provider;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users/ providers")
public class ProviderController {
    @Autowired
    private ProviderService service;


    public ResponseEntity<List<Provider>> listAll(){
        return ResponseEntity.ok(service.lisAll());
    }

    public ResponseEntity<Optional<Provider>> getById(Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    public ResponseEntity<Provider> filterByLicenseNumber(String licenseNumber){
        return ResponseEntity.ok(service.filterByLicenseNumber(licenseNumber));
    }








}
