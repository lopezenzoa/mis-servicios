package com.group.mis_servicios.service;

import com.group.mis_servicios.dto.LoginDTO;
import com.group.mis_servicios.dto.RegistroDTO;
import com.group.mis_servicios.entity.Credentials;
import com.group.mis_servicios.entity.User;
import com.group.mis_servicios.repository.CredentialsRepository;
import com.group.mis_servicios.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired private CredentialsRepository credentialsRepository;
    @Autowired private BCryptPasswordEncoder encoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registrarUsuario(RegistroDTO dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());

        Credentials credentials = new Credentials();
        credentials.setUsername(dto.getUsername());
        credentials.setPassword(encoder.encode(dto.getPassword()));
        credentials.setUser(user);

        user.setCredentials(credentials);

        userRepository.save(user);
    }

    public boolean login(LoginDTO dto) {
        String identificador = dto.getIdentificador();
        String password = dto.getPassword();

        Optional<User> userOpt = identificador.contains("@") ?
                userRepository.findByEmail(identificador) :
                userRepository.findByUsername(identificador);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return passwordEncoder.matches(password, user.getCredentials().getPassword());
        }

        return false;
    }


    public List<User> obtenerUsuarios() {
        return userRepository.findAll();
    }
}