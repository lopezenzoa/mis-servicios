package com.group.mis_servicios.service.validators;

import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.model.repository.ReviewRepository;
import com.group.mis_servicios.view.dto.ReviewDTO;

public class ReviewValidator {

    public static boolean checkValidity(ReviewDTO dto, CustomerRepository cRepo, ProviderRepository pRepo) {
        return !dto.getDescription().isEmpty()
                && !dto.getDescription().isBlank()
                && cRepo.existsById(dto.getCustomerId())
                && pRepo.existsById(dto.getProviderId());
    }
}
