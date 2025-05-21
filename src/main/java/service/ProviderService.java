package service;

import entity.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ProviderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {
    @Autowired
    private ProviderRepository providerRepository;


    public List<Provider> lisAll(){
       return providerRepository.findAll();
    }

    public Optional<Provider> getById(Long id){
        return providerRepository.findById(id);
    }

    public Provider filterByLicenseNumber(String licenseNumber){
      return  providerRepository.findByLicenseNumber(licenseNumber).orElse(null);
    }

}
