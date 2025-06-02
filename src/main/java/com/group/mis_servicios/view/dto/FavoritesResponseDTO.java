package com.group.mis_servicios.view.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class FavoritesResponseDTO {
    private Long id;
    private String title;
    private LocalDateTime creationDate;
    private Long clientId;
    private List<ProviderResponseDTO> providers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<ProviderResponseDTO> getProviders() {
        return providers;
    }

    public void setProviders(List<ProviderResponseDTO> providers) {
        this.providers = providers;
    }
}
