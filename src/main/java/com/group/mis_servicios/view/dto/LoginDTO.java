package com.group.mis_servicios.view.dto;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginDTO {
    private String username;
    private String password;
}