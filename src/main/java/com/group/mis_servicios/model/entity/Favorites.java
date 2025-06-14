package com.group.mis_servicios.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "favorites")
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_id", nullable = false)
    private Long id;

    @Column(length = 40, nullable = false, unique = true)
    private String title;

    @Column(name = "creation_date", columnDefinition = "DATETIME")
    private LocalDateTime creationDate;

    @OneToOne
    //@JoinColumn(name = "customer_id", insertable = false, updatable = false)  //Esto hace que no se pueda persistir correctamente la relación con el Customer
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // JPA automatically creates service_provider junction table
    @ManyToMany
    @JoinTable(
            name = "favorites_provider",  // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "list_id"),  // FK que referencia a la tabla Prestador
            inverseJoinColumns = @JoinColumn(name = "provider_id")// FK que referencia a la tabla Servicio
    )
    private List<Provider> providers;
}
