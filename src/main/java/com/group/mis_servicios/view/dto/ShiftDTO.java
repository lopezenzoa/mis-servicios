package com.group.mis_servicios.view.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShiftDTO {
    private Integer providerId;
    private LocalDateTime dateTime;
    private boolean isAvailable;
}