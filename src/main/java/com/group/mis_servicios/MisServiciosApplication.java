package com.group.mis_servicios;

import com.group.mis_servicios.entity.Category;
import com.group.mis_servicios.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MisServiciosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MisServiciosApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CategoryRepository categoryRepository) {
		return args -> {
			if (categoryRepository.count() == 0) {
				categoryRepository.save(new Category());
				categoryRepository.save(new Category());
				categoryRepository.save(new Category());
			}
		};
	}
}
