package com.group.mis_servicios.model.entity;

import com.group.mis_servicios.model.enums.States;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "calls")
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "call_id", nullable = false)
    private Long id;

    @NotBlank
    @Column(length = 255)
    private String description;

    @Column(columnDefinition = "DATETIME", nullable = false)
    @Future(message = "The date must be in the future")
    private LocalDateTime date;

    @NotBlank
    @Column(length = 40, nullable = false)
    private String address;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private States state;


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "provider_id", updatable = false)
    private Provider provider;
}
