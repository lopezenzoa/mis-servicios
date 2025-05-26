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

    @Column(name = "facility_id", nullable = false)
    private Integer facilityId;

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "facility_id", insertable = false, updatable = false)
    private Facility facility;

    @OneToMany(mappedBy = "provider")
    private List<Shift> shifts;

    @OneToMany(mappedBy = "provider")
    private List<Call> calls;
}
