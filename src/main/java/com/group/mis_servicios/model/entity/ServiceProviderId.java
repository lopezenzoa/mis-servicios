package com.group.mis_servicios.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class ServiceProviderId implements Serializable {
    @Column(name = "user_id", nullable = false)
    private Integer providerId;
    @Column(name = "service_id", nullable = false)
    private Integer serviceId;

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        ServiceProviderId that = (ServiceProviderId) object;
        return Objects.equals(providerId, that.providerId) && Objects.equals(serviceId, that.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerId, serviceId);
    }
}
