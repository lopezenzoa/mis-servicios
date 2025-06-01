package com.group.mis_servicios.view.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class FavoritesResponseDTO {
    private Long id;
    private String title;
    private LocalDateTime creationDate;
    private Long customerId;
    private List<ProviderResponseDTO> providers;
}
