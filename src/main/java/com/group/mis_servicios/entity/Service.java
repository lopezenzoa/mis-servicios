package com.group.mis_servicios.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    public Service(String name, String description, Provider provider) {
        this.name = name;
        this.description = description;
        this.provider = provider;
    }

    @ManyToOne
    @JoinColumn(name = "provider_id")
    @JsonBackReference
    private Provider provider;
}
