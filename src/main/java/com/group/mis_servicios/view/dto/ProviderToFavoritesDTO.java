package com.group.mis_servicios.view.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

@Getter
@Setter
public class ProviderToFavoritesDTO {
    @Positive
    @NotBlank(message = "The favorites list cannot be in blank")
    private Long favoritesListId;

    @Positive
    @NotBlank(message = "The provider cannot be in blank")
    private Long providerId;

    public Long getFavoritesListId() {
        return favoritesListId;
    }

    public void setFavoritesListId(Long favoritesListId) {
        this.favoritesListId = favoritesListId;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }
}
