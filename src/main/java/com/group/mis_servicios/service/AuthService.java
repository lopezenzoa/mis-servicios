package com.group.mis_servicios.service;

import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.enums.Roles;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.service.validators.AuthValidator;
import com.group.mis_servicios.view.dto.LoginDTO;
import com.group.mis_servicios.view.dto.RegisterDTO;
import com.group.mis_servicios.model.entity.Credentials;
import com.group.mis_servicios.model.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PasswordEncoder passwordEncoder;

    public void register(RegisterDTO dto, Roles role) {
        boolean isValid = AuthValidator.checkRegisterValidity(dto, credsRepo);

        System.out.println(isValid);

        if (isValid) {
            Credentials creds = new Credentials();

            creds.setUsername(dto.getUsername());
            creds.setPassword(encoder.encode(dto.getPassword()));
            creds.setRole(role); // this param is sent in the customer/provider service

            System.out.println(creds);

            if (role.equals(Roles.CUSTOMER)) {
                Optional<Customer> customerOpt = customerRepo.findByEmail(dto.getEmail());

                if (customerOpt.isPresent()) {
                    creds.setCustomer(customerOpt.get());
                    customerOpt.get().setCredentials(creds);
                    credsRepo.save(creds);
                }
            } else {
                Optional<Provider> providerOpt = providerRepo.findByEmail(dto.getEmail());

                if (providerOpt.isPresent()) {
                    creds.setProvider(providerOpt.get());
                    providerOpt.get().setCredentials(creds);
                    credsRepo.save(creds);
                }
            }
        }
    }

    public boolean login(LoginDTO dto) {
        String identifier = dto.getIdentifier();
        String password = dto.getPassword();

        // by this way, the user can log in with your email or username
        Optional<Credentials> credsOpt = credsRepo.findByUsername(identifier);

        return credsOpt.filter(credentials -> passwordEncoder.matches(password, credentials.getPassword())).isPresent();
    }
}