package com.group.mis_servicios.service.mappers;

import com.group.mis_servicios.model.entity.Call;
import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.view.dto.CallDTO;
import com.group.mis_servicios.view.dto.CallResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CallMapper {

    public static CallDTO toDTO(Call call) {
        CallDTO callDTO = new CallDTO();

        callDTO.setId(call.getId());
        callDTO.setDescription(call.getDescription());
        callDTO.setAddress(call.getAddress());
        callDTO.setDate(call.getDate());
        callDTO.setState(call.getState());



        return callDTO;
    }

    public static CallResponseDTO toResponseDTO(Call call) {
        CallResponseDTO callDTO = new CallResponseDTO();

        callDTO.setId(call.getId());
        callDTO.setDescription(call.getDescription());
        callDTO.setAddress(call.getAddress());
        callDTO.setDate(call.getDate());
        callDTO.setState(call.getState());

        return callDTO;
    }

    public static List<CallResponseDTO> toCallDTOList(List<Call> calls) {
        List<CallResponseDTO> dtos = new ArrayList<>();

        calls.forEach(call -> {
            dtos.add(toResponseDTO(call));
        });

        return dtos;
    }

    public static Call toCall(CallDTO callDTO, CustomerRepository customerRepo, ProviderRepository providerRepo) {
        Call call = new Call();
        Optional<Customer> customerOpt = customerRepo.findById(callDTO.getCustomerId());
        Optional<Provider> providerOpt = providerRepo.findById(callDTO.getProviderId());

        if (customerOpt.isPresent() && providerOpt.isPresent()) {
            call.setId(callDTO.getId());
            call.setDescription(callDTO.getDescription());
            call.setAddress(callDTO.getAddress());
            call.setDate(callDTO.getDate());
            call.setState(callDTO.getState());
            call.setCustomer(customerOpt.get());
            call.setProvider(providerOpt.get());
        }

        return call;
    }
}
