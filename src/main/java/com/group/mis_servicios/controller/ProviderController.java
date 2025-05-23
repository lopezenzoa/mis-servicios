package com.group.mis_servicios.controller;

import com.group.mis_servicios.dto.ProviderDTO;
import com.group.mis_servicios.dto.ProviderResponseDTO;
import com.group.mis_servicios.entity.Category;
import com.group.mis_servicios.entity.Credentials;
import com.group.mis_servicios.entity.Provider;
import com.group.mis_servicios.repository.CategoryRepository;
import com.group.mis_servicios.repository.ProviderRepository;
import com.group.mis_servicios.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/providers")
public class ProviderController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ProviderService service;

    @GetMapping("/sin-paginacion")
    public ResponseEntity<List<Provider>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Provider> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/license")
    public ResponseEntity<Provider> filterByLicenseNumber(@RequestParam String licenseNumber) {
        return ResponseEntity.ok(service.filterByLicenseNumber(licenseNumber));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderDTO> updateProfile(@PathVariable Long id, @RequestBody ProviderDTO updated) {
        return new ResponseEntity<>(service.update(id, updated), HttpStatus.OK);
    }
    @GetMapping("/{services}")
    public ResponseEntity<List<ProviderDTO>> filterByServices(@PathVariable String services){
        return ResponseEntity.ok(service.filterByServices(services));
    }

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

}
