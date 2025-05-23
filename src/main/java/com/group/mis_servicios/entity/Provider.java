package com.group.mis_servicios.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@Entity
public class Provider extends User {
    private String licenseNumber;
    @OneToMany(mappedBy = "provider")
    @JsonManagedReference
    private List<Service> services;

    @OneToMany(mappedBy = "provider")
    private List<Shift> shifts;


}
