package com.group.mis_servicios.config;

import com.group.mis_servicios.model.entity.Categoria;
import com.group.mis_servicios.model.repository.CategoriaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostConstruct
    public void cargarDatosIniciales() {
        if (categoriaRepository.count() == 0) {
            List<String> categorias = List.of(
                    "Electricista",
                    "Gasista",
                    "Plomero",
                    "Pintor",
                    "Jardinero",
                    "Paseador de perros",
                    "Niñera",
                    "Profesor particular",
                    "Servicios de Limpieza",
                    "Maguiver",
                    "Albanil",
                    "Cerrajero"

            );

            for (String nombre : categorias) {
                Categoria categoria = new Categoria();
                categoria.setNombre(nombre);
                categoriaRepository.save(categoria);
            }
        }
    }
}
