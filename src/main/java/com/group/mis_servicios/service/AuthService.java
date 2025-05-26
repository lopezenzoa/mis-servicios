package com.group.mis_servicios.service;

import com.group.mis_servicios.view.dto.LoginDTO;
import com.group.mis_servicios.view.dto.RegisterDTO;
import com.group.mis_servicios.model.entity.Credentials;
import com.group.mis_servicios.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.group.mis_servicios.model.repository.CredentialsRepository;
import com.group.mis_servicios.model.repository.UserRepository;

import java.util.List;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired private CredentialsRepository credentialsRepository;
    @Autowired private BCryptPasswordEncoder encoder; // to encrypt the password

    public void register(RegisterDTO dto) {
        User user = new User();
        Credentials credentials = new Credentials();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setPhoneNumber(dto.getPhoneNumber());

        credentials.setUsername(dto.getUsername());
        credentials.setPassword(encoder.encode(dto.getPassword()));
        credentials.setUser(user);

        userRepository.save(user);
    }

    public boolean login(LoginDTO dto) {
        return credentialsRepository.findByUsername(dto.getUsername())
                .map(c -> encoder.matches(dto.getPassword(), c.getPassword()))
                .orElse(false);
    }

    public List<User> getAuthUsers() {
        return userRepository.findAll();
    }
}