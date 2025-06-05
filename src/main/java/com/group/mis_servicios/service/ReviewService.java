package com.group.mis_servicios.service;

import com.group.mis_servicios.model.entity.Review;
import com.group.mis_servicios.model.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    // crud
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }
    //crear


    //eliminar

    // modificar

    // filtrar por prestador

}
