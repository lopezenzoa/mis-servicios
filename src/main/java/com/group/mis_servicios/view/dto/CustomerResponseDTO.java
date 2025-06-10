package com.group.mis_servicios.view.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerResponseDTO extends UserResponseDTO {
    private List<CallDTO> calls;
    private List<ReviewDTO> reviews;

    public List<CallDTO> getCalls() {
        return calls;
    }

    public void setCalls(List<CallDTO> calls) {
        this.calls = calls;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }
}
