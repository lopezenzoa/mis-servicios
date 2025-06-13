package com.group.mis_servicios.view.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class UserDTO {
    @NotBlank(message = "The first name cannot be blank")
    private String firstName;

    @NotBlank(message = "The last name cannot be blank")
    private String lastName;

    @Email
    @NotBlank(message = "The email name cannot be blank")
    private String email;

    @NotBlank(message = "The address name cannot be blank")
    private String address;

    @NotBlank(message = "The phone number cannot be blank")
    private String phoneNumber;

    @NotBlank(message = "The username cannot be blank")
    private String username;

    @NotBlank(message = "The password cannot be blank")
    @Pattern(regexp = "\\d{8,16}", message = "The password must contain at least 8 characters")
    private String password;
}
