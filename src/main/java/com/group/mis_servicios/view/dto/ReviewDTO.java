package com.group.mis_servicios.view.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDTO {
    @Positive
    @NotBlank(message="The id cannot be in blank")
    private long id;

    @NotBlank(message = "The description cannot be in blank")
    private String description;

    @NotBlank(message= "The customer user name cannot be in blank")
    private String customerUsername;

    @FutureOrPresent
    @NotBlank(message= "The date time cannot be in blank")
    private LocalDateTime dateTime;

}
