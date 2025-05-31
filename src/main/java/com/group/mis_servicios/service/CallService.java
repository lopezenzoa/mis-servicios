package com.group.mis_servicios.service;

import ch.qos.logback.classic.spi.CallerData;
import com.group.mis_servicios.model.entity.Call;
import com.group.mis_servicios.model.repository.CallRepository;
import com.group.mis_servicios.view.dto.CallDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CallService {

    @Autowired
    private CallRepository repository;



    public CallDTO create(CallDTO call) {
        return callMapper(repository.save(callMapper(call)));
    }

    public CallDTO update(Long id, CallDTO updatedCall) {
        Optional<Call> optionalCall = repository.findById(id);
        if ( optionalCall.isPresent()){
            Call call= repository.save(callMapper(updatedCall));
            return callMapper(call);
        } else {
            System.out.println("The id " + id + " does not exist");
        }
        return null; // deberiamos manejar el error (je) y no retornar null
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

    public void deleteById(Long id) {

        if(repository.existsById(id)){
            repository.deleteById(id);
            System.out.println("The id " + id + " has been deleted");
        }else {
            System.out.println("The id " + id + " does not exist");
        }
        // tambien manejar la exception
    }

    private CallDTO callMapper (Call call) {
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

}
