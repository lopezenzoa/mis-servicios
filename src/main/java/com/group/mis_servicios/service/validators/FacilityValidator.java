package com.group.mis_servicios.service.validators;

import com.group.mis_servicios.view.dto.FacilityDTO;

public class FacilityValidator {
    public static boolean checkValidity(FacilityDTO dto) {
        return !dto.getName().isBlank();
    }
}
