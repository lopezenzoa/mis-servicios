package com.group.mis_servicios.view.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDTO {
    private Long id;

    @NotBlank(message = "Description is required")
    private String description;

    private LocalDateTime reviewDate;

    @NotNull(message = "Customer ID is required")
    @Positive(message = "The customer id must be a positive number")
    private Long customerId;

    @NotNull(message = "Provider ID is required")
    @Positive(message = "The provider id must be a positive number")
    private Long providerId;

}
