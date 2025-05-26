package com.group.mis_servicios.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShiftDTO {
    private Long providerId;
    private LocalDateTime dateTime;
}
