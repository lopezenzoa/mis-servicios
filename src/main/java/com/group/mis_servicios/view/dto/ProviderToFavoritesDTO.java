package com.group.mis_servicios.view.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderToFavoritesDTO {
    private Long favoritesListId;
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
