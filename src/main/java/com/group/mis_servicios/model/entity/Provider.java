package com.group.mis_servicios.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "providers")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(nullable = false, length = 40, unique = true)
    private String email;

    @Column(nullable = false, length = 40, unique = true)
    private String address;

    @Column(name = "phone_number", nullable = false, length = 40, unique = true)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "credential_id")
    private Credentials credentials;

    @Column(name = "license_number", nullable = false, unique = true)
    private String licenseNumber;

    @Column(length = 40, nullable = false)
    private String facility;

    @OneToMany(mappedBy = "provider")  // Relación inversa, donde "prestador" es el campo en la entidad Turno
    private List<Shift> shifts;

    @OneToMany(mappedBy = "provider")
    private List<Call> calls;
}
