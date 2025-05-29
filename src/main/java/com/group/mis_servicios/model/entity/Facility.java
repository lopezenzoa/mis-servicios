package com.group.mis_servicios.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "facility")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id", nullable = false)
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String name;
    @Column(length = 20)
    private String description;

    // Simple Many-to-Many - JPA handles junction table
    @OneToMany(mappedBy = "facility")
    private List<Provider> providers;
}
