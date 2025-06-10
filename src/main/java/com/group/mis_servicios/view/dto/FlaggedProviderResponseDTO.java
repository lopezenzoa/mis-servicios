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

    public String getProviderFirstName() {
        return providerFirstName;
    }

    public void setProviderFirstName(String providerFirstName) {
        this.providerFirstName = providerFirstName;
    }

    public String getProviderLastName() {
        return providerLastName;
    }

    public void setProviderLastName(String providerLastName) {
        this.providerLastName = providerLastName;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
