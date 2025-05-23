package com.group.mis_servicios.service;

import com.group.mis_servicios.dto.ProviderDTO;
import com.group.mis_servicios.entity.Shift;
import com.group.mis_servicios.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShiftService {
    @Autowired
    private ShiftRepository repository;

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
}
