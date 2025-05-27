package com.group.mis_servicios.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.group.mis_servicios.dto.ProviderResponseDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class FavoritesList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime creationDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonBackReference
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
            name = "favorites_list_providers",
            joinColumns = @JoinColumn(name = "favorites_list_id"),
            inverseJoinColumns = @JoinColumn(name = "provider_id")
    )
    private List<Provider> providersList = new ArrayList<>();
}
