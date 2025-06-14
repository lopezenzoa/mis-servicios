package com.group.mis_servicios.view.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "The identifier cannot be in blank")
    private String identifier;

    @NotBlank(message = "The password cannot be in blank")
    private String password;
}