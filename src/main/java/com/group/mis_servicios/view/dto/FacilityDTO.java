package com.group.mis_servicios.view.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FacilityDTO {
    @NotBlank(message = "The facility name cannot be in blank")
    private String name;
    @Max(180)
    private String description;
}
