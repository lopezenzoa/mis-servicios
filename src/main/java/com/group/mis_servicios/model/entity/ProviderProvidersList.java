package com.group.mis_servicios.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "provider_providers_list")
public class ProviderProvidersList {
    @EmbeddedId
    @Column(name = "provider_providers_list_id", nullable = false)
    private ProviderProvidersListId id;

    @ManyToOne
    @JoinColumn(name = "list_id", referencedColumnName = "list_id", insertable = false, updatable = false)
    private ProvidersList list;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Provider provider;
}
