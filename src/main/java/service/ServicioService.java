package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ServiceRepository;

@Service
public class ServicioService {

    @Autowired
    private ServiceRepository serviceRepository;

}
