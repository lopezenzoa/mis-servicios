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
@Table(name = "providers_list")
public class ProvidersList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_id", nullable = false)
    private Integer id;

    @Column(name = "creation_date", columnDefinition = "DATETIME")
    private LocalDateTime creationDate;

    @Column(name = "user_id", nullable = false)
    private Integer ownerId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Customer customer;

    @OneToMany(mappedBy = "list", cascade = {CascadeType.ALL})
    private List<ProviderProvidersList> providers;
}
