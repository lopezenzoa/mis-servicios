package com.group.mis_servicios.view.dto;

import com.group.mis_servicios.model.enums.States;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CallDTO {
    private Long id;

    private String description;

    @Future(message = "The date must be in the future")
    private LocalDateTime date;

    @NotBlank(message = "The address cannot be in blank")
    private String address;

    @NotNull(message = "The state must not be null")
    private States state;


    @Positive
    private Long customerId;

    @Positive
    private Long providerId;
}
