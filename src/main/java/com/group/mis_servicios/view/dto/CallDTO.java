package com.group.mis_servicios.view.dto;

import com.group.mis_servicios.enums.States;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CallDTO {
    private Long id;
    @Max(180)
    private String description;

    @Future(message = "The date must be in the future")
    @NotBlank(message = "The date cannot be in blank")
    private LocalDateTime date;

    @NotBlank(message = "The address cannot be in blank")
    private String address;

    @NotBlank(message = "The state cannot be in blank")
    private States state;

    @Positive
    @NotBlank(message = "The customer cannot be in blank")
    private Long customerId;

    @Positive
    @NotBlank(message = "The provider cannot be in blank")
    private Long providerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }
}
