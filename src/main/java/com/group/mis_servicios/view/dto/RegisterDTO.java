package com.group.mis_servicios.view.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegisterDTO {
    @Size(min = 4, max = 40, message = "El nombre de usuario debe tener entre 4 y 40 caracteres")
    @NotBlank(message = "El nombre de usuario no puede estar en blanco")
    private String username;


    @NotBlank(message = "The password cannot be in blank")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$",
            message = "The password must have at least 8 characters, including uppercase, lowercase, number, and special character."
    )
    private String password;

    @NotBlank(message = "The first name cannot be in blank")
    private String firstName;

    @NotBlank(message = "The last name cannot be in blank")
    private String lastName;

    @NotBlank(message = "The email cannot be in blank")
    @Email
    private String email;

    @NotBlank(message = "The address cannot be in blank")
    private String address;

    @NotBlank(message = "The phone number cannot be in blank")
    private String phoneNumber;

    @Size(max = 40, message = "The license number is too long")
    private String licenseNumber;

    @Size(max = 40, message = "The facility name is too long")
    private String facility;
}