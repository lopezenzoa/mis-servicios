package com.group.mis_servicios.view.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FavoritesDTO {
    private Long id;
    @NotBlank(message = "The title cannot be in blank")
    private String title;
    @Positive
    @NotBlank(message = "The customer cannot be in blank")
    private Long customerId;

    @FutureOrPresent
    @NotBlank(message = "The creation date cannot be in blank")
    private LocalDateTime creationDate;

    private List<ProviderResponseDTO> providers;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
