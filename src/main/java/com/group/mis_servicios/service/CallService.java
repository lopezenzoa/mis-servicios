package com.group.mis_servicios.service;

import ch.qos.logback.classic.spi.CallerData;
import com.group.mis_servicios.enums.States;
import com.group.mis_servicios.model.entity.Call;
import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.repository.CallRepository;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.view.dto.CallDTO;
import com.group.mis_servicios.view.dto.CustomerResponseDTO;
import com.group.mis_servicios.view.dto.ProviderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.nimbus.State;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CallService implements I_Service<CallDTO> {
    @Autowired
    private CallRepository repository;
    @Autowired
    private ProviderRepository providerRepo;
    @Autowired
    private CustomerRepository customerRepo;

    @Override
    public List<CallDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::callMapper)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CallDTO> getById(Long id) {
        return repository.findById(id).map(this::callMapper);
    }

    @Override
    public Optional<CallDTO> create(CallDTO call) {
        boolean valid = checkCallValidity(call);

        if (valid) {
            Call saved = repository.save(callMapper(call));
            return Optional.of(callMapper(saved));
        }

        return Optional.empty();
    }

    @Override
    public Optional<CallDTO> update(Long id, CallDTO updatedCall) {
        Optional<Call> optionalCall = repository.findById(id);

        if (optionalCall.isPresent()){
            Call call= repository.save(callMapper(updatedCall));
            return Optional.of(callMapper(call));
        }


        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }

        return false;
    }

    /* Los mappers deberian ser abstraidos */
    private CallDTO callMapper(Call call) {
        CallDTO callDTO = new CallDTO();

        callDTO.setId(call.getId());
        callDTO.setDescription(call.getDescription());
        callDTO.setDate(call.getDate());
        callDTO.setState(States.valueOf(call.getState()));
        callDTO.setCustomerId(call.getCustomer().getId());
        callDTO.setProviderId(call.getCustomer().getId());

        return callDTO;
    }

    private Call callMapper (CallDTO callDTO) {
        Call call = new Call();
        Optional<Customer> customerOpt = customerRepo.findById(callDTO.getCustomerId());
        Optional<Provider> providerOpt = providerRepo.findById(callDTO.getProviderId());

        if (customerOpt.isPresent() && providerOpt.isPresent()) {
            call.setId(callDTO.getId());
            call.setDescription(callDTO.getDescription());
            call.setDate(callDTO.getDate());
            call.setState(callDTO.getState().toString());
            call.setCustomer(customerOpt.get());
            call.setProvider(providerOpt.get());
        }

        return call;
    }

    private boolean checkCallValidity(CallDTO dto) {
        return !dto.getDate().isBefore(LocalDateTime.now())
                && !dto.getAddress().isBlank()
                && !dto.getState().toString().isBlank()
                && providerExists(dto.getProviderId());
    }

    private boolean providerExists(Long providerId) {
        return providerRepo.existsById(providerId);
    }
}
