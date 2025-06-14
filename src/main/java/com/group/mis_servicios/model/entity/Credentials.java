package com.group.mis_servicios.model.entity;

import com.group.mis_servicios.model.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "credentials")
public class Credentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credential_id")
    private Long credentialId;

    @NotBlank
    @Column(length = 100, nullable = false, unique = true)
    private String username;

    @NotBlank
    @Column(length = 100, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15)", nullable = false)
    private Roles role;

    @OneToOne(mappedBy = "credentials")
    private Customer customer;

    @OneToOne(mappedBy = "credentials")
    private Provider provider;
}

