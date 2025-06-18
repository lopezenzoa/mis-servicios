package com.group.mis_servicios.view.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderToFavoritesDTO {


    @Positive(message = "The favorites list id must be a positive number")
    private Long favoritesListId;

    @Positive(message = "The provider id must be a positive number")
    private Long providerId;

}
