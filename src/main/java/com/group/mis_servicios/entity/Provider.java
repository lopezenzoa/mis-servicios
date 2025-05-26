package com.group.mis_servicios.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @OneToMany(mappedBy = "provider")
    private List<Shift> shifts;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


}
