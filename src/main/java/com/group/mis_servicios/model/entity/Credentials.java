package com.group.mis_servicios.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "credential")
public class Credentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credential_id", nullable = false)
    private Integer id;

    @Column(length = 20, nullable = false, unique = true)
    private String username;
    @Column(length = 20, nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;
}

