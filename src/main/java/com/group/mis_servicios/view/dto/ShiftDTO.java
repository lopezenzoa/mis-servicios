package com.group.mis_servicios.view.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShiftDTO {
    private Long providerId;
    private String dateTime;
    private boolean isAvailable;
}