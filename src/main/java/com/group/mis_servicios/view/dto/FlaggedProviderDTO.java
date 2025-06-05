package com.group.mis_servicios.view.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FlaggedProviderDTO {
    private Long customerId;
    private Long providerId;
    private String reason;
}
