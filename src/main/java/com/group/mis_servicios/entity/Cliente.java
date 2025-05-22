package com.group.mis_servicios.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String email;
    private String direccion;
    private String telefono;


    @OneToMany(mappedBy = "cliente")
    private List<Service> servicios;
}
