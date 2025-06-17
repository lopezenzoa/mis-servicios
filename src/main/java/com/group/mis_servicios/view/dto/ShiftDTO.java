package com.group.mis_servicios.view.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShiftDTO {

    @Positive(message = "The shift id must be positive")
    private Long id;

    @Positive(message = "The provider id must be positive")
    private Long providerId;

    @NotBlank(message = "The date cannot be in blank")
    private String dateTime;

    private Boolean isAvailable;
}
