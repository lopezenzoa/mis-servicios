package com.group.mis_servicios.view.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShiftDTO {
    @Positive
    @NotBlank(message = "The provider cannot be in blank")
    private Long providerId;

    @NotBlank(message = "The date cannot be in blank")
    private String dateTime;

    private boolean isAvailable;
}