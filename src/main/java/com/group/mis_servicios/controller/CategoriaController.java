package com.group.mis_servicios.controller;


import com.group.mis_servicios.model.entity.Categoria;
import com.group.mis_servicios.model.repository.CategoriaRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@CrossOrigin("*")
@Tag(name = "Categorias", description = "Operaciones relacionadas con la categoría")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Lista todas las categorias")
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }
}