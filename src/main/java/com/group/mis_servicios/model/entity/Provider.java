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

    // JPA automatically creates service_provider junction table
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "facility_provider",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id")
    )
    private List<Facility> facilities;

    @OneToMany(mappedBy = "provider")
    private List<Shift> shifts;

    @OneToMany(mappedBy = "provider")
    private List<Call> calls;
}
