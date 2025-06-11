package com.group.mis_servicios.service;

import com.group.mis_servicios.service.mappers.ShiftMapper;
import com.group.mis_servicios.service.validators.ShiftValidator;
import com.group.mis_servicios.view.dto.ShiftDTO;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.entity.Shift;
import com.group.mis_servicios.model.repository.ShiftRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.view.dto.FacilityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShiftService implements I_Service<ShiftDTO> {
    @Autowired
    private ShiftRepository repository;
    @Autowired
    private ProviderRepository providerRepository;

    @Override
    public List<ShiftDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(ShiftMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<ShiftDTO> getById(Long id) {
        Optional<Shift> shift = repository.findById(id);

        return shift.map(ShiftMapper::toDTO);
    }

    @Override
    public Optional<ShiftDTO> create(ShiftDTO shift) {
        Optional<Shift> optionalShift = Optional.of(repository.save(ShiftMapper.toShift(shift)));

        return optionalShift.map(ShiftMapper::toDTO);
    }

    @Override
    public Optional<ShiftDTO> update(Long id, ShiftDTO updated) {
        Optional<Shift> shiftOptional = repository.findById(id);

        if (shiftOptional.isPresent()
                && ShiftValidator.checkProvider(updated.getProviderId())
                && !ShiftValidator.existsShiftAtSameTime(updated.getProviderId(), LocalDateTime.parse(updated.getDateTime()))
        ) {
            Shift saved = repository.save(ShiftMapper.toShift(updated));

            return Optional.of(ShiftMapper.toDTO(saved));
        }

        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        Optional<Shift> shiftOptional = repository.findById(id);

        if (shiftOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        }

        return false;
    }

    public List<ShiftDTO> getAvailables() {
        return repository.findAll()
                .stream()
                .filter(Shift::isAvailable)
                .map(ShiftMapper::toDTO)
                .toList();
    }

    public List<ShiftDTO> getAvailableByProvider(Long providerId) {
        return repository.findByProviderIdAndAvailableTrue(providerId).stream().map(ShiftMapper::toDTO).toList();
    }
}
