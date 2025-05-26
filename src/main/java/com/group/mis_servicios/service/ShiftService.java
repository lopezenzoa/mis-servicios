package com.group.mis_servicios.service;

import com.group.mis_servicios.dto.ShiftDTO;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.entity.Shift;
import com.group.mis_servicios.model.repository.ShiftRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.view.dto.FacilityDTO;
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

    public Shift getById(Integer id) {
        Optional<Shift> shift = repository.findById(id);

        return shift.orElseGet(Shift::new);
    }

    public Shift create(Shift shift) {
        return repository.save(shift);
    }

    public Shift update(Integer id, Shift updated) {
        Optional<Shift> shiftOptional = repository.findById(id);

        if (shiftOptional.isPresent()) {
            return repository.save(updated);
        }

        return new Shift();
    }

    public boolean delete(Integer id) {
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
            throw new RuntimeException("Oops! There are another shift for that provider at that time");
        }

        Provider provider = providerRepository.findById(dto.getProviderId())
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        Shift shift = new Shift();

        shift.setDateTime(dto.getDateTime());
        shift.setAvailable(true);
        shift.setProvider(provider);

        return repository.save(shift);
    }

    public List<Shift> getAvailableByProvider(Integer providerId) {
        return repository.findByProviderIdAndAvailableTrue(providerId);
    }

    public boolean existsShiftAtSameTime(Integer providerId, LocalDateTime dateTime) {
        return repository.existsByProviderIdAndDateTime(providerId, dateTime);
    }



}
