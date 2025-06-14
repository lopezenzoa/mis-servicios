package com.group.mis_servicios.view.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderToFavoritesDTO {
 //   @Positive
//    @NotBlank(message = "The favorites list cannot be in blank")
//    private Long favoritesListId;

//    @Positive
//    @NotBlank(message = "The provider cannot be in blank") //  NO usar @NotBlank en Long
//    private Long providerId;

    @Positive(message = "The favorites list id must be a positive number")
    private Long favoritesListId;

    @Positive(message = "The provider id must be a positive number")
    private Long providerId;

}
