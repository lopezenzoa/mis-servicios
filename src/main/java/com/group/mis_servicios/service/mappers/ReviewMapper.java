package com.group.mis_servicios.service.mappers;

import com.group.mis_servicios.model.entity.Review;
import com.group.mis_servicios.view.dto.ReviewDTO;

import java.util.ArrayList;
import java.util.List;

public class ReviewMapper {
    public static ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();

        dto.setId(review.getId());
        dto.setDescription(review.getDescription());

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
