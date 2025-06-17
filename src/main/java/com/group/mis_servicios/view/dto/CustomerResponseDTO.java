package com.group.mis_servicios.view.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerResponseDTO extends UserResponseDTO {
    private List<CallResponseDTO> calls;
    private List<ReviewDTO> reviews;
    private FavoritesDTO favorites;
}
