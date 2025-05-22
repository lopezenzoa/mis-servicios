package com.group.mis_servicios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.group.mis_servicios.repository.ServiceRepository;

@Service
public class ServicioService {

    @Autowired
    private ServiceRepository serviceRepository;

}
