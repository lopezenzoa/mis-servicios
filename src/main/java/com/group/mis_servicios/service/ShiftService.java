package com.group.mis_servicios.service;

import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.entity.Shift;
import com.group.mis_servicios.model.enums.States;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.model.repository.ShiftRepository;
import com.group.mis_servicios.service.mappers.ShiftMapper;
import com.group.mis_servicios.service.validators.ShiftValidator;
import com.group.mis_servicios.view.dto.ShiftDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShiftService implements I_Service<ShiftDTO> {
    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    private ProviderRepository providerRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<ShiftDTO> getAll() {
        return shiftRepository.findAll()
                .stream()
                .map(ShiftMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<ShiftDTO> getById(Long id) {
        Optional<Shift> shift = shiftRepository.findById(id);

        return shift.map(ShiftMapper::toDTO);
    }

    @Override
    public Optional<ShiftDTO> create(ShiftDTO shift) {
        if (!providerRepository.existsById(shift.getProviderId())) {
            throw new RuntimeException("Proveedor con ID " + shift.getProviderId() + " no encontrado.");
        }

        Shift newShift = ShiftMapper.toShift(shift, providerRepository);
        Shift saved = shiftRepository.save(newShift);
        return Optional.of(ShiftMapper.toDTO(saved));
    }


    @Override
    public Optional<ShiftDTO> update(Long id, ShiftDTO updated) {
        Optional<Shift> shiftOptional = shiftRepository.findById(id);

        if (shiftOptional.isPresent()
                && ShiftValidator.checkProvider(updated.getProviderId(), providerRepository)
                && !ShiftValidator.existsShiftAtSameTime(updated.getProviderId(), LocalDateTime.parse(updated.getDateTime()), shiftRepository)) {

            shiftRepository.deleteById(id);

            Shift saved = shiftRepository.save(ShiftMapper.toShift(updated, providerRepository));
            return Optional.of(ShiftMapper.toDTO(saved));
        }

        return Optional.empty();
    }

    public List<ShiftDTO> createMultiple(List<ShiftDTO> shifts) {
        return shifts.stream()
                .map(dto -> {
                    try {
                        return this.create(dto);
                    } catch (Exception e) {
                        System.out.println("Error al crear shift: " + dto);
                        e.printStackTrace();
                        return Optional.<ShiftDTO>empty();
                    }
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }


    @Override
    public boolean delete(Long id) {
        Optional<Shift> shiftOptional = shiftRepository.findById(id);

        if (shiftOptional.isPresent()) {
            shiftRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public List<ShiftDTO> getAvailables() {
        return shiftRepository.findAll()
                .stream()
                .filter(Shift::getAvailable)
                .map(ShiftMapper::toDTO)
                .toList();
    }

    public List<ShiftDTO> getAvailableByProvider(Long providerId) {
        return shiftRepository.findByProviderIdAndAvailableTrue(providerId).stream().map(ShiftMapper::toDTO).toList();
    }
    public Optional<ShiftDTO> reserveShift(Long shiftId) {
        Optional<Shift> shiftOptional = shiftRepository.findById(shiftId);

        if (shiftOptional.isPresent()) {
            Shift shift = shiftOptional.get();
            if (!shift.getAvailable()) {
                return Optional.empty();
            }


            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            var customer = customerRepository.findByCredentials_Username(username)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));


            shift.setCustomer(customer);
            shift.setAvailable(false); // Marcar como reservado

            Shift saved = shiftRepository.save(shift);
            return Optional.of(ShiftMapper.toDTO(saved));
        }

        return Optional.empty(); // No existe
    }

    public List<ShiftDTO> getAllByProvider(Long providerId) {
        return shiftRepository.findByProviderId(providerId)
                .stream()
                .map(ShiftMapper::toDTO)
                .toList();
    }


    public List<ShiftDTO> getAllByCustomerUsername(String username) {
        var customer = customerRepository.findByCredentials_Username(username)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        return shiftRepository.findByCustomerId(customer.getId())
                .stream()
                .map(ShiftMapper::toDTO)
                .toList();
    }


    public void aceptarTurno(Long shiftId, String providerUsername) {
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));

        if (shift.getCustomer() == null) {
            throw new RuntimeException("Este turno no ha sido reservado aún");
        }

        Provider provider = providerRepository.findByCredentials_Username(providerUsername)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        if (!shift.getProvider().getId().equals(provider.getId())) {
            throw new RuntimeException("No tenés permiso para aceptar este turno");
        }


        shift.setStatus(States.ACCEPTED);


        shift.setAvailable(false);

        shiftRepository.save(shift);
    }


}
