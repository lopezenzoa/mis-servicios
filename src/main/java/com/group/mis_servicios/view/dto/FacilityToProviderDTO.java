package com.group.mis_servicios.view.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacilityToProviderDTO {
    private Long providerId;
    private Long facilityId;

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }
}
