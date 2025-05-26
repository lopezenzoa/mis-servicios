package com.group.mis_servicios.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private String username;
    private String password;
}
