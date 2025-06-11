package com.group.mis_servicios.service;

import ch.qos.logback.classic.spi.CallerData;
import com.group.mis_servicios.enums.States;
import com.group.mis_servicios.model.entity.Call;
import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.repository.CallRepository;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.service.mappers.CallMapper;
import com.group.mis_servicios.service.validators.CallValidator;
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
                .map(CallMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CallDTO> getById(Long id) {
        return repository.findById(id).map(CallMapper::toDTO);
    }

    @Override
    public Optional<CallDTO> create(CallDTO call) {
        boolean valid = CallValidator.checkCallValidity(call);

        if (valid) {
            Call saved = repository.save(CallMapper.toCall(call));
            return Optional.of(CallMapper.toDTO(saved));
        }

        return Optional.empty();
    }

    @Override
    public Optional<CallDTO> update(Long id, CallDTO updatedCall) {
        Optional<Call> optionalCall = repository.findById(id);

        if (optionalCall.isPresent()){
            Call call= repository.save(CallMapper.toCall(updatedCall));
            return Optional.of(CallMapper.toDTO(call));
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
}
