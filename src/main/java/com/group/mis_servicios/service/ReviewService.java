package com.group.mis_servicios.service;

import com.group.mis_servicios.model.entity.Review;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.model.repository.ReviewRepository;
import com.group.mis_servicios.service.mappers.ReviewMapper;
import com.group.mis_servicios.service.validators.ReviewValidator;
import com.group.mis_servicios.view.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService implements I_Service<ReviewDTO> {
    @Autowired
    private ReviewRepository repository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProviderRepository providerRepository;


    @Override
    public List<ReviewDTO> getAll() {
        List<ReviewDTO> reviews = new ArrayList<>();
        repository.findAll()
                .forEach(review -> reviews.add(ReviewMapper.toDTO(review)));

        return reviews;
    }

    @Override
    public Optional<ReviewDTO> getById(Long id) {
        Optional<Review> review = repository.findById(id);

        return review.map(ReviewMapper::toDTO);
    }

    @Override
    public Optional<ReviewDTO> create(ReviewDTO dto) {
        boolean isValid = ReviewValidator.checkValidity(dto, customerRepository, providerRepository);
        if (!isValid)
            return Optional.empty();

        dto.setReviewDate(LocalDateTime.now());
        Review review = repository.save(ReviewMapper.toReview(dto, customerRepository, providerRepository));

        return Optional.of(ReviewMapper.toDTO(review));
    }

    @Override
    public Optional<ReviewDTO> update(Long id, ReviewDTO updatedDto) {
        Optional<Review> review = repository.findById(id);

        if(review.isPresent()) {
            repository.deleteById(id);
            Review saved = repository.save(ReviewMapper.toReview(updatedDto, customerRepository, providerRepository));
            return Optional.of(ReviewMapper.toDTO(saved));
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Review> filterByProvider(Long providerId) {
        return  repository.findByProviderId(providerId);
    }

}
