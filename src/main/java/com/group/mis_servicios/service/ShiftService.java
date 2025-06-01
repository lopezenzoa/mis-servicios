package com.group.mis_servicios.service;

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
public class ShiftService {
    @Autowired
    private ShiftRepository repository;
    @Autowired
    private ProviderRepository providerRepository;

    public List<ShiftDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::shiftMapper)
                .toList();
    }

    public Optional<ShiftDTO> getById(Long id) {
        Optional<Shift> shift = repository.findById(id);

        return shift.map(this::shiftMapper);
    }

    public Optional<ShiftDTO> create(Shift shift) {
        Optional<Shift> optionalShift = Optional.of(repository.save(shift));

        return optionalShift.map(this::shiftMapper);
    }

    public Optional<ShiftDTO> update(Long id, Shift updated) {
        Optional<Shift> shiftOptional = repository.findById(id);

        if (shiftOptional.isPresent()) {
            return Optional.of(shiftMapper(repository.save(updated)));
        }

        return Optional.empty();
    }

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
                .map(this::shiftMapper)
                .toList();
    }

    public Optional<ShiftDTO> createShiftForProvider(ShiftDTO dto) {
        if (existsShiftAtSameTime(dto.getProviderId(), LocalDateTime.parse(dto.getDateTime()))) {
            return Optional.empty();
        }

        Optional<Provider> provider = providerRepository.findById(dto.getProviderId());

        if (provider.isEmpty())
            return Optional.empty();

        Shift shift = new Shift();

        shift.setDateTime(LocalDateTime.parse(dto.getDateTime()));
        shift.setAvailable(true);
        shift.setProviderId(provider.get().getId());
        shift.setProvider(provider.get());

        return Optional.of(shiftMapper(repository.save(shift)));
    }

    public List<ShiftDTO> getAvailableByProvider(Long providerId) {
        return repository.findByProviderIdAndAvailableTrue(providerId).stream().map(this::shiftMapper).toList();
    }

    public boolean existsShiftAtSameTime(Long providerId, LocalDateTime dateTime) {
        return repository.existsByProviderIdAndDateTime(providerId, dateTime);
    }

    public ShiftDTO shiftMapper(Shift shift) {
        ShiftDTO dto = new ShiftDTO();

        dto.setProviderId(shift.getProvider().getId());
        dto.setDateTime(shift.getDateTime().toString());
        dto.setAvailable(shift.isAvailable());

        return dto;
    }
}
