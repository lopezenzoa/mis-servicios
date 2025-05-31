package com.group.mis_servicios.view.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CallDTO {
    private Long id;
    private String description;
    private LocalDateTime date;
    private String address;
    private String state;
    private Long customerId;
    private Long providerId;
}
