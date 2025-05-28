package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.Credentials;
import com.group.mis_servicios.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByCredentials(Credentials credentials);
}
