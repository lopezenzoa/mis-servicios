package com.group.mis_servicios.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
}
