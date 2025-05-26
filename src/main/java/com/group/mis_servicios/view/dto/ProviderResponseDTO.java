package com.group.mis_servicios.view.dto;

import com.group.mis_servicios.model.entity.Facility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProviderResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String licenseNumber;
    private String facility;
}
