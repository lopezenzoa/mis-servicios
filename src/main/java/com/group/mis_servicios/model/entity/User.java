package com.group.mis_servicios.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(nullable = false, length = 40, unique = true)
    private String email;

    @Column(nullable = false, length = 40, unique = true)
    private String address;

    @OneToOne
    @JoinColumn(name = "credential_id", referencedColumnName = "credential_id", insertable = false, updatable = false)
    private Credentials credentials;
}