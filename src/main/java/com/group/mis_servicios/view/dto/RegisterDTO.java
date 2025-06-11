package com.group.mis_servicios.view.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegisterDTO {
    @Max(value = 30, message = "The username must contains less than 30 characters")
    @NotBlank(message = "The username cannot be in blank")
    private String username;

    @Pattern(regexp = "\\d{8,16}", message = "The password must contain at least 8 characters")
    @NotBlank(message = "The password cannot be in blank")
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}