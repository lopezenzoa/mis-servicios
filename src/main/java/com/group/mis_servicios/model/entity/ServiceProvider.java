package com.group.mis_servicios.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "service_provider")
public class ServiceProvider {
    @EmbeddedId
    @Column(name = "service_provider_id")
    private ServiceProviderId id;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "service_id", insertable = false, updatable = false)
    private Service service;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Provider provider;
}
