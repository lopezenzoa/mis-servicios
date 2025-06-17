package com.group.mis_servicios.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "shifts")
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", columnDefinition = "DATETIME", nullable = false, unique = true)
    private LocalDateTime dateTime;

    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "provider_id")  // FK a la tabla Prestador
    private Provider provider;
}
