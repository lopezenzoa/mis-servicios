package com.group.mis_servicios.config;


import com.group.mis_servicios.entity.Category;
import com.group.mis_servicios.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {
        if (categoryRepository.count() == 0) {
            Category c1 = new Category();
            c1.setNombre("Electricista");
            c1.setDescripcion("Instalaciones eléctricas");

            Category c2 = new Category();
            c2.setNombre("Plomero");
            c2.setDescripcion("Servicios sanitarios");

            Category c3 = new Category();
            c3.setNombre("Gasista");
            c3.setDescripcion("Revisión de gas");

            categoryRepository.save(c1);
            categoryRepository.save(c2);
            categoryRepository.save(c3);

            System.out.println("Categorías precargadas.");
        }
    }
}
