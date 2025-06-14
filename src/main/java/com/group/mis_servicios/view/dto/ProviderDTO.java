package com.group.mis_servicios.view.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProviderDTO extends UserDTO {
    @NotBlank(message = "The license number cannot be blank")
    @Pattern(regexp = "\\d{5,10}", message = "The license must contain at least 5 numbers")
    private String licenseNumber;
    private String facility;
}
