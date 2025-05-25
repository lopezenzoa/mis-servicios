package com.group.mis_servicios.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class ProviderProvidersListId {
    @Column(name = "list_id", nullable = false)
    private Integer providersListId;

    @Column(name = "provider_id", nullable = false)
    private Integer providerId;

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        ProviderProvidersListId that = (ProviderProvidersListId) object;
        return Objects.equals(providersListId, that.providersListId) && Objects.equals(providerId, that.providerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providersListId, providerId);
    }
}
