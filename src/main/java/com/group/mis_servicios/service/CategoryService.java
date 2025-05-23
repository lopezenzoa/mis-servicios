package com.group.mis_servicios.service;

import com.group.mis_servicios.entity.Category;
import com.group.mis_servicios.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoriaRepository;

    public Category crearCategoria(Category categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Category> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Category> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    public Category actualizarCategoria(Long id, Category nuevaCategoria) {
        return categoriaRepository.findById(id)
                .map(c -> {
                    c.setNombre(nuevaCategoria.getNombre());
                    return categoriaRepository.save(c);
                })
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }
}
