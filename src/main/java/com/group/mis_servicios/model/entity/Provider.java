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
public class Provider extends User {
    @Column(name = "license_number", nullable = false, unique = true)
    private String licenseNumber;

    @OneToMany(mappedBy = "provider", cascade = {CascadeType.ALL})
    private List<FacilityProvider> services;

    @OneToMany(mappedBy = "provider")
    private List<Shift> shifts;

    @OneToMany(mappedBy = "provider")
    private List<Call> calls;
}
