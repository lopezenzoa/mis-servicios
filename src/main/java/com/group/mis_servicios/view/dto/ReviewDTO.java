package com.group.mis_servicios.view.dto;

import com.group.mis_servicios.model.entity.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

public class ReviewDTO {
    private Long id;

    private String description;

    @NotBlank(message = "The customer id cannot be blank")
    private Long customerId;
}
