package com.group.mis_servicios.dto;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter


public class LoginDTO {
    private String username;
    private String password;


}