package com.group.mis_servicios.service;

import com.group.mis_servicios.entity.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.group.mis_servicios.repository.ProviderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {
    @Autowired
    private ProviderRepository providerRepository;


    public List<Provider> listAll(){
       return providerRepository.findAll();
    }

    public Optional<Provider> getById(Long id){
        return providerRepository.findById(id);
    }

    public Provider filterByLicenseNumber(String licenseNumber){
      return  providerRepository.findByLicenseNumber(licenseNumber).orElse(null);
    }
    public Provider save(Provider provider) {
        return providerRepository.save(provider);
    }

}
