package com.group.mis_servicios.service;

import com.group.mis_servicios.model.entity.Credentials;
import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.enums.Roles;
import com.group.mis_servicios.model.repository.CredentialsRepository;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.security.JwtUtil;
import com.group.mis_servicios.view.dto.LoginDTO;
import com.group.mis_servicios.view.dto.LoginResponseDTO;
import com.group.mis_servicios.view.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired private CustomerRepository customerRepo;
    @Autowired private ProviderRepository providerRepo;
    @Autowired private CredentialsRepository credsRepo;
    @Autowired private BCryptPasswordEncoder encoder; // to encrypt the password
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegisterDTO dto, Roles role) {
        // Validación de unicidad de username
        if (credsRepo.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        // Validación de unicidad de email
        if (role.equals(Roles.CUSTOMER) && customerRepo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado como cliente");
        }

        if (role.equals(Roles.PROVIDER) && providerRepo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado como prestador");
        }

        // Crear credenciales
        Credentials creds = new Credentials();
        creds.setUsername(dto.getUsername());
        creds.setPassword(encoder.encode(dto.getPassword()));
        creds.setRole(role);

        if (role.equals(Roles.CUSTOMER)) {

            Customer nuevo = new Customer();
            nuevo.setFirstName(dto.getFirstName());
            nuevo.setLastName(dto.getLastName());
            nuevo.setEmail(dto.getEmail());
            nuevo.setAddress(dto.getAddress());
            nuevo.setPhoneNumber(dto.getPhoneNumber());

            customerRepo.save(nuevo);


            nuevo.setCredentials(creds);
            creds.setCustomer(nuevo);
            credsRepo.save(creds);

        } else if (role.equals(Roles.PROVIDER)) {

            Provider nuevo = new Provider();
            nuevo.setFirstName(dto.getFirstName());
            nuevo.setLastName(dto.getLastName());
            nuevo.setEmail(dto.getEmail());
            nuevo.setAddress(dto.getAddress());
            nuevo.setPhoneNumber(dto.getPhoneNumber());
            nuevo.setLicenseNumber(dto.getLicenseNumber());
            nuevo.setFacility(dto.getFacility());

            providerRepo.save(nuevo);


            nuevo.setCredentials(creds);
            creds.setProvider(nuevo);
            credsRepo.save(creds);
        }
    }


    public LoginResponseDTO loginConToken(LoginDTO dto) {
        String identifier = dto.getIdentifier();
        String password = dto.getPassword();

        Optional<Credentials> credsOpt = credsRepo.findByUsername(identifier);

        if (credsOpt.isEmpty()) {
            Optional<Customer> customer = customerRepo.findByEmail(identifier);
            Optional<Provider> provider = providerRepo.findByEmail(identifier);

            if (customer.isPresent() && customer.get().getCredentials() != null) {
                credsOpt = Optional.of(customer.get().getCredentials());
            } else if (provider.isPresent() && provider.get().getCredentials() != null) {
                credsOpt = Optional.of(provider.get().getCredentials());
            }
        }

        if (credsOpt.isPresent() && passwordEncoder.matches(password, credsOpt.get().getPassword())) {
            Credentials credentials = credsOpt.get();

            String role = "ROLE_" + credentials.getRole().name();
            String token = jwtUtil.generateToken(
                    credentials.getUsername(),
                    List.of(new SimpleGrantedAuthority(role))
            );

            return new LoginResponseDTO(token, role);
        }

        throw new RuntimeException("Usuario o contraseña inválidos");
    }
    public String getRoleByUsername(String username) {
        boolean esCliente = customerRepository.existsByCredentials_Username(username);
        boolean esPrestador = providerRepository.existsByCredentials_Username(username);

        if (esCliente) return "CLIENTE";
        if (esPrestador) return "PROVEEDOR";
        return "UNKNOWN";
    }


}