package com.group.mis_servicios.view.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class FlaggedProviderResponseDTO {
    private String providerFirstName;
    private String providerLastName;
    private String customerFirstName;
    private String customerLastName;
    private String reason;
    private LocalDateTime date;
}
