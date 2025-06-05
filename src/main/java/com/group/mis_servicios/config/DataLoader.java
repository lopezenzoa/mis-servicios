package com.group.mis_servicios.config;


import com.group.mis_servicios.entity.Service;
import com.group.mis_servicios.repository.ServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ServiceRepository serviceRepository;

    public DataLoader(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void run(String... args) {
        if (serviceRepository.count() == 0) {
            serviceRepository.save(new Service(null, "Plomero", null));
            serviceRepository.save(new Service(null, "Gasista", null));
            serviceRepository.save(new Service(null, "Electricista", null));
            serviceRepository.save(new Service(null, "Jardinero", null));
            serviceRepository.save(new Service(null, "Maguiver", null));
            serviceRepository.save(new Service(null, "Pintor", null));
            serviceRepository.save(new Service(null, "Paseador de perros", null));
            serviceRepository.save(new Service(null, "Servicios de limpieza", null));

        }
    }
}

