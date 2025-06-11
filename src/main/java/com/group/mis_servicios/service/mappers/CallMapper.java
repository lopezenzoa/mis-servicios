package com.group.mis_servicios.service.mappers;

import com.group.mis_servicios.enums.States;
import com.group.mis_servicios.model.entity.Call;
import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.view.dto.CallDTO;

import java.util.Optional;

public class CallMapper {
    private static CustomerRepository customerRepo;
    private static ProviderRepository providerRepo;

    public static CallDTO toDTO(Call call) {
        CallDTO callDTO = new CallDTO();

        callDTO.setId(call.getId());
        callDTO.setDescription(call.getDescription());
        callDTO.setDate(call.getDate());
        callDTO.setState(States.valueOf(call.getState()));
        callDTO.setCustomerId(call.getCustomer().getId());
        callDTO.setProviderId(call.getCustomer().getId());

        return callDTO;
    }

    public static Call toCall(CallDTO callDTO) {
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
}
