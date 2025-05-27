package com.group.mis_servicios.service;

import com.group.mis_servicios.view.dto.LoginDTO;
import com.group.mis_servicios.view.dto.RegisterDTO;
import com.group.mis_servicios.model.entity.Credentials;
import com.group.mis_servicios.model.entity.User;
import com.group.mis_servicios.model.repository.CredentialsRepository;
import com.group.mis_servicios.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.group.mis_servicios.model.repository.CredentialsRepository;
import com.group.mis_servicios.model.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired private CredentialsRepository credentialsRepository;
    @Autowired private BCryptPasswordEncoder encoder; // to encrypt the password

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        user.setCredentials(credentials);

        userRepository.save(user);
    }

    public boolean login(LoginDTO dto) {
        String identifier = dto.getIdentificador();
        String password = dto.getPassword();

        // by this way, the user can log in with your email or usrename
        Optional<User> userOpt = identifier.contains("@") ?
                userRepository.findByEmail(identifier) :
                userRepository.findByCredentials(credentialsRepository.findByUsername(identifier).get());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return passwordEncoder.matches(password, user.getCredentials().getPassword());
        }

        return false;
    }


    public List<User> getAuthUsers() {
        return userRepository.findAll();
    }
}