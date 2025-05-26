package com.group.mis_servicios.controller;

import com.group.mis_servicios.view.dto.ProviderDTO;
import com.group.mis_servicios.view.dto.ProviderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.group.mis_servicios.service.ProviderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/providers")
@CrossOrigin("*")
public class ProviderController {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ProviderService service;

    @GetMapping("/")
    public ResponseEntity<List<ProviderResponseDTO>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Optional<ProviderResponseDTO> providerOptional = service.getById(id);

        if (providerOptional.isPresent())
            return new ResponseEntity<>(providerOptional.get(), HttpStatus.OK);
        else {
            return new ResponseEntity<>("The provider has no been found with the ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/license/{licenseNumber}")
    public ResponseEntity<ProviderDTO> filterByLicenseNumber(@PathVariable String licenseNumber) {
        return new ResponseEntity<>(service.filterByLicenseNumber(licenseNumber), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderResponseDTO> updateProfile(@PathVariable Integer id, @RequestBody ProviderDTO updated) {
        return new ResponseEntity<>(service.update(id, updated), HttpStatus.OK);
    }
    @GetMapping("/services/{services}")
    public ResponseEntity<List<ProviderResponseDTO>> filterByServices(@PathVariable String services){
        return ResponseEntity.ok(service.filterByFacility(services));
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

    // manejo de excecpciones
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "The parameter '" + ex.getName() + "' must be a valid number (Long).";
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
*/
}
