package com.group.mis_servicios.service;

import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.entity.Flagged;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.model.repository.FlaggedProviderRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.view.dto.FlaggedProviderDTO;
import com.group.mis_servicios.view.dto.FlaggedProviderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FlaggedProviderService {
    @Autowired
    private FlaggedProviderRepository flaggedProviderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProviderRepository providerRepository;

    public void flagProvider(FlaggedProviderDTO dto) {
        if (flaggedProviderRepository.existsByCustomerIdAndProviderId(dto.getCustomerId(), dto.getProviderId())) {
            throw new RuntimeException("You already flagged this provider.");
        }

        Optional<Customer> customerOpt = customerRepository.findById(dto.getCustomerId());
        if (customerOpt.isEmpty()) {
            throw new RuntimeException("Customer not found!");
        }

        Optional<Provider> providerOpt = providerRepository.findById(dto.getProviderId());
        if(providerOpt.isEmpty()){
            throw new RuntimeException("Provider not found!");
        }

        Flagged flagged = new Flagged();
        flagged.setCustomer(customerOpt.get());
        flagged.setProvider(providerOpt.get());
        flagged.setReason(dto.getReason());
        flagged.setReportDate(LocalDateTime.now());
        flaggedProviderRepository.save(flagged);
    }

    public List<FlaggedProviderResponseDTO> getProviderFlags(Long providerId) {
        List<Flagged> flags = flaggedProviderRepository.findByProviderId(providerId);

        return flags.stream().map(f -> {
            FlaggedProviderResponseDTO dto = new FlaggedProviderResponseDTO();
            dto.setCustomerFirstName(f.getCustomer().getFirstName());
            dto.setCustomerLastName(f.getCustomer().getLastName());
            dto.setProviderFirstName(f.getProvider().getFirstName());
            dto.setProviderLastName((f.getProvider().getLastName()));
            dto.setReason(f.getReason());
            dto.setDate(f.getReportDate());
            return dto;
        }).toList();
    }

    public long getFlagCount(Long providerId) {
        return flaggedProviderRepository.countByProviderId(providerId);
    }

}
