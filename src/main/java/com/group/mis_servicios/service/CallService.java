package com.group.mis_servicios.service;

import ch.qos.logback.classic.spi.CallerData;
import com.group.mis_servicios.model.entity.Call;
import com.group.mis_servicios.model.repository.CallRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.view.dto.CallDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CallService {

    @Autowired
    private CallRepository repository;
    @Autowired
    private ProviderRepository providerRepository;

    public Optional<CallDTO> create(CallDTO call) {
        boolean valid = checkCallValidity(call);

        if (valid) {
            Call saved = repository.save(callMapper(call));
            return Optional.of(callMapper(saved));
        }

        return Optional.empty();
    }

    public Optional<CallDTO> update(Long id, CallDTO updatedCall) {
        Optional<Call> optionalCall = repository.findById(id);

        if (optionalCall.isPresent()){
            Call call= repository.save(callMapper(updatedCall));
            return Optional.of(callMapper(call));
        }


        return Optional.empty();
    }

    public Optional<CallDTO> findById(Long id) {
        return repository.findById(id).map(this::callMapper);
    }

    public List<CallDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(this::callMapper)
                .collect(Collectors.toList());
    }

    public boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }

        return false;
    }

    private CallDTO callMapper(Call call) {
        CallDTO callDTO = new CallDTO();

        callDTO.setId(call.getId());
        callDTO.setDescription(call.getDescription());
        callDTO.setDate(call.getDate());
        callDTO.setState(call.getState());
        callDTO.setCustomerId(call.getCustomerId());
        callDTO.setProviderId(call.getProviderId());

        return callDTO;
    }

    private Call callMapper (CallDTO callDTO) {
        Call call = new Call();
        call.setId(callDTO.getId());
        call.setDescription(callDTO.getDescription());
        call.setDate(callDTO.getDate());
        call.setState(callDTO.getState());
        call.setCustomerId(callDTO.getCustomerId());
        call.setProviderId(callDTO.getProviderId());
        return call;
    }

    private boolean checkCallValidity(CallDTO dto) {
        return !dto.getDate().isBefore(LocalDateTime.now())
                && !dto.getAddress().isBlank()
                && !dto.getState().isBlank()
                && providerExists(dto.getProviderId());
    }

    private boolean providerExists(Long providerId) {
        return providerRepository.existsById(providerId);
    }
}
