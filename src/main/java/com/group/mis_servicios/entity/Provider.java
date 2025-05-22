package com.group.mis_servicios.entity;

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
    private List<Service> services;
    // private List<Shift> shifts;


}
