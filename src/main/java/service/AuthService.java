package service;

import dto.RegistroDTO;
import entity.Credentials;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repository.CredentialsRepository;
import repository.UserRepository;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired private CredentialsRepository credentialsRepository;
    @Autowired private BCryptPasswordEncoder encoder;

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
        return credentialsRepository.findByUsername(dto.getUsername())
                .map(c -> encoder.matches(dto.getPassword(), c.getPassword()))
                .orElse(false);
    }
}