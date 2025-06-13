package com.group.mis_servicios.view.dto;

import com.group.mis_servicios.model.entity.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDTO {
    private Long id;

    private String description;

    @NotBlank(message = "The customer id cannot be blank")
    private Long customerId;
}
