package com.group.mis_servicios.service.mappers;

import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.entity.Review;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.view.dto.ReviewDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReviewMapper {

    public static Review toReview(ReviewDTO dto, CustomerRepository customerRepository, ProviderRepository providerRepository) {
        Customer customer= customerRepository.getReferenceById(dto.getCustomerId());
        Provider provider= providerRepository.getReferenceById(dto.getProviderId());

        Review review = new Review();
        review.setId(dto.getId());
        review.setDescription(dto.getDescription());
        review.setCreationDate(dto.getReviewDate());
        review.setCustomer(customer);
        review.setProvider(provider);

        return review;
    }

    public static ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();

        dto.setId(review.getId());
        dto.setDescription(review.getDescription());
        dto.setReviewDate(review.getCreationDate());
        dto.setCustomerId(review.getCustomer().getId());
        dto.setProviderId(review.getProvider().getId());

        return dto;
    }

    public static List<ReviewDTO> toReviewDTOList(List<Review> reviews) {
        List<ReviewDTO> dtos = new ArrayList<>();

        reviews.forEach(review -> {
            dtos.add(toDTO(review));
        });

        return dtos;
    }
}
