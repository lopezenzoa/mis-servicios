package com.group.mis_servicios.controller;

import com.group.mis_servicios.service.ReviewService;
import com.group.mis_servicios.service.mappers.ReviewMapper;
import com.group.mis_servicios.view.dto.ReviewDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
@Tag(name = "Review", description = "Operaciones relacionadas con las reseñas")
public class ReviewController {

    @Autowired
    private ReviewService serviceR;



    @GetMapping("/")
    @ApiResponse(responseCode = "200", description = "Obtiene todas las reseñas")
    public ResponseEntity<List<ReviewDTO>> getAll() {
        return ResponseEntity.ok(serviceR.getAll());
    }

    @GetMapping("/provider/{id}")
    @ApiResponse(responseCode = "200",description = "Obtiene todas las reseñas por provider ID")
    @ApiResponse (responseCode = "404", description = "No se pudo filtrar las reseñas por provider ID")
    public ResponseEntity<?> getAllByProviderId(@PathVariable("id") Long providerId) {
        List<ReviewDTO> list =
                serviceR.filterByProvider(providerId)
                .stream().map(ReviewMapper::toDTO)
                .toList();
        if (list.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "Provider has not been found!"));
        }

        return ResponseEntity.ok(list);
    }

    @PostMapping("/create")
    @ApiResponse(responseCode = "200", description = "Reseña creada")
    @ApiResponse(responseCode = "400", description = "La reseña no puedo crearse")
    public ResponseEntity<?> create (
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cuerpo de la reseña a crear")
            @RequestBody ReviewDTO dto) {
       Optional<ReviewDTO> reviewOp = serviceR.create(dto);
       if (reviewOp.isPresent()) {
           return ResponseEntity.ok()
                   .body(Map.of("message", "Review has been created successfully!"));
       }
        return ResponseEntity.status(400)
                .body(Map.of("message", "Review has not been created!"));
    }

    @PutMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Reseña actualizada")
    @ApiResponse(responseCode = "400", description = "La reseña no se pudo actualizar")
    public ResponseEntity<?> update (
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cuerpo del prestador a actualizar")
            @PathVariable Long id,
            @RequestBody ReviewDTO dto){
        Optional<ReviewDTO> reviewDTO = serviceR.update(id, dto);
        if (reviewDTO.isPresent()) {
            return ResponseEntity.ok()
                    .body(Map.of("message", "Review has been updated successfully!"));
        }
        return ResponseEntity.status(400)
                .body(Map.of("message", "Review has not been updated!"));
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Reseña eliminada")
    @ApiResponse(responseCode = "400", description = "La reseña no se pudo eliminarse")
    public ResponseEntity<?> delete ( @PathVariable Long id){
        if(serviceR.getById(id).isPresent()){
            serviceR.delete(id);
            return ResponseEntity.ok()
                    .body(Map.of("message", "Review has been deleted successfully!"));
        }
       return ResponseEntity.status(HttpStatus.NOT_FOUND)
               .body(Map.of("message", "Review has not been deleted!"));
    }

}
