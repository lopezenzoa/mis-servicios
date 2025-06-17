package com.group.mis_servicios.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(nullable = false, length = 40, unique = true)
    @Email(message = "The email is not valid")
    private String email;

    @Column(nullable = false, length = 40, unique = true)
    private String address;

    @Column(name = "phone_number", nullable = false, length = 40, unique = true)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "credential_id")
    private Credentials credentials;

    @OneToMany(mappedBy = "customer")
    private List<Review> reviews;

    @OneToOne(mappedBy = "customer")
    private Favorites favorites;

    @OneToMany(mappedBy = "customer")
    private List<Call> calls;
    @OneToMany(mappedBy = "customer")
    private List<Shift> shifts;
}
