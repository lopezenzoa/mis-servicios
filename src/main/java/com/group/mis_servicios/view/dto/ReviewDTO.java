package com.group.mis_servicios.view.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDTO {
    private Long id;

    private String description;

    @Positive(message = "The customer id must be a positive number")
    private Long customerId;

}
