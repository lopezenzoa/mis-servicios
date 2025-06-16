package com.group.mis_servicios.view.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProviderResponseDTO extends UserResponseDTO {

    private String licenseNumber;
    private String facility;
}
