package com.group.mis_servicios.view.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FavoritesDTO {
    private Long id;
    @NotBlank(message = "The title cannot be in blank")
    private String title;
   // @Positive
//  NO usar @NotBlank acá porque es Long
// @NotBlank(message = "The customer cannot be in blank")
   // private Long customerId;

  //  @FutureOrPresent
//  NO usar @NotBlank acá porque es LocalDateTime
// @NotBlank(message = "The creation date cannot be in blank")
    //private LocalDateTime creationDate;

    @Positive(message = "The customer id must be a positive number")
    private Long customerId;

    @FutureOrPresent(message = "The creation date must be in the present or future")
    private LocalDateTime creationDate;

    private List<ProviderResponseDTO> providers;
}
