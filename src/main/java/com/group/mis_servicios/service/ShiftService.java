package com.group.mis_servicios.service;

import com.group.mis_servicios.dto.ProviderDTO;
import com.group.mis_servicios.dto.ShiftDTO;
import com.group.mis_servicios.entity.Provider;
import com.group.mis_servicios.entity.Shift;
import com.group.mis_servicios.repository.ProviderRepository;
import com.group.mis_servicios.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShiftService {
    @Autowired
    private ShiftRepository repository;
    @Autowired
    private ProviderRepository providerRepository;

    public List<Shift> getAll() {
        return repository.findAll();
    }

    public Shift getById(Long id) {
        Optional<Shift> shift = repository.findById(id);

        return shift.orElseGet(Shift::new);
    }

    public Shift create(Shift shift) {
        return repository.save(shift);
    }

    public Shift update(Long id, Shift updated) {
        Optional<Shift> shiftOptional = repository.findById(id);

        if (shiftOptional.isPresent()) {
            return repository.save(updated);
        }

        return new Shift();
    }

    public boolean delete(Long id) {
        Optional<Shift> shiftOptional = repository.findById(id);

        if (shiftOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        }

        return false;
    }

    public List<Shift> getAvailables() {
        return repository.findAll()
                .stream()
                .filter(Shift::isAvailable)
                .toList();
    }

    public Shift createShiftForProvider(ShiftDTO dto) {
        if (existsShiftAtSameTime(dto.getProviderId(), dto.getDateTime())) {
            throw new RuntimeException("Ya existe un turno en ese horario para este prestador.");
        }

        Provider provider = providerRepository.findById(dto.getProviderId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Shift shift = new Shift();
        shift.setDateTime(dto.getDateTime());
        shift.setAvailable(true);
        shift.setProvider(provider);

        return repository.save(shift);
    }

    public List<Shift> getAvailableByProvider(Long providerId) {
        return repository.findByProviderIdAndAvailableTrue(providerId);
    }

    public boolean existsShiftAtSameTime(Long providerId, LocalDateTime dateTime) {
        return repository.existsByProviderIdAndDateTime(providerId, dateTime);
    }



}
