package com.group.mis_servicios.controller;

import com.group.mis_servicios.view.dto.ProviderDTO;
import com.group.mis_servicios.model.entity.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.group.mis_servicios.service.ProviderService;
import com.group.mis_servicios.model.entity.Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@RestController
@RequestMapping("/providers")
@CrossOrigin("*")
public class ProviderController {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ProviderService service;

    @GetMapping("/")
    public ResponseEntity<List<ProviderDTO>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderDTO> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @GetMapping("/license/{licenseNumber}")
    public ResponseEntity<ProviderDTO> filterByLicenseNumber(@PathVariable String licenseNumber) {
        return new ResponseEntity<>(service.filterByLicenseNumber(licenseNumber), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderDTO> updateProfile(@PathVariable Integer id, @RequestBody ProviderDTO updated) {
        return new ResponseEntity<>(service.update(id, updated), HttpStatus.OK);
    }
    @GetMapping("/{services}")
    public ResponseEntity<List<ProviderDTO>> filterByServices(@PathVariable String services){
        return ResponseEntity.ok(service.filterByServices(services));
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody ProviderDTO dto) {
        service.create(dto);
        return new ResponseEntity<>("The provider has been created successfully", HttpStatus.OK);
    }

    /*
    @PostMapping("/register")
    public ResponseEntity<ProviderResponseDTO> registerProvider(@RequestBody ProviderDTO dto) {
        Category categoria = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Provider provider = new Provider();
        provider.setFirstName(dto.getFirstName());
        provider.setLastName(dto.getLastName());
        provider.setEmail(dto.getEmail());
        provider.setAddress(dto.getAddress());
        provider.setLicenseNumber(dto.getLicenseNumber());
        provider.setCategory(categoria);

        Credentials credentials = new Credentials();
        credentials.setUsername(dto.getUsername());
        credentials.setPassword(encoder.encode(dto.getPassword()));
        credentials.setUser(provider);

        provider.setCredentials(credentials);

        Provider saved = providerRepository.save(provider);

        ProviderResponseDTO response = new ProviderResponseDTO();
        response.setId(saved.getId());
        response.setFirstName(saved.getFirstName());
        response.setLastName(saved.getLastName());
        response.setEmail(saved.getEmail());
        response.setAddress(saved.getAddress());
        response.setLicenseNumber(saved.getLicenseNumber());
        response.setCategoryName(categoria.getNombre());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/por-categoria")
    public ResponseEntity<List<ProviderResponseDTO>> getPorCategoria(@RequestParam String nombreCategoria) {
        return ResponseEntity.ok(service.buscarPorNombreCategoria(nombreCategoria));
    }
    @GetMapping("/buscar")
    public ResponseEntity<List<ProviderResponseDTO>> buscar(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String licenseNumber
    ) {
        return ResponseEntity.ok(service.buscarPorCriterios(firstName, lastName, email, licenseNumber));
    }
    @GetMapping
    public ResponseEntity<Page<ProviderResponseDTO>> listAllPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.listarPaginado(pageable));
    }
*/
}
