package com.group.mis_servicios.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "shift")
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", columnDefinition = "DATETIME", nullable = false, unique = true)
    private LocalDateTime dateTime;

    @Column(columnDefinition = "TINYINT")
    private boolean available;

    @Column(name = "provider_id", nullable = false)
    private Long providerId;

    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "provider_id", insertable = false, updatable = false)
    private Provider provider;
}
