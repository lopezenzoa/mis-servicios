package com.group.mis_servicios.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String address;


}