package com.group.mis_servicios.service;

import com.group.mis_servicios.model.entity.Shift;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.model.repository.ShiftRepository;
import com.group.mis_servicios.service.mappers.ShiftMapper;
import com.group.mis_servicios.service.validators.ShiftValidator;
import com.group.mis_servicios.view.dto.ShiftDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
                .filter(Shift::isAvailable)
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
            if (!shift.isAvailable()) {
                return Optional.empty(); // Ya reservado
            }

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

}
