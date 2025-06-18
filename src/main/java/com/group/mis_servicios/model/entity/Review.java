package com.group.mis_servicios.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Long id;

    @Column(length = 255, nullable = false)
    private String description;

    @Column(name = "creation_date", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne

    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


    @ManyToOne(optional = false)
    @JoinColumn(name = "provider_id", nullable = false, updatable = false)
    private Provider provider;
}
