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

    public Optional<ShiftDTO> getById(Integer id) {
        Optional<Shift> shift = repository.findById(id);

        return shift.map(this::shiftMapper);
    }

    public Optional<ShiftDTO> create(Shift shift) {
        Optional<Shift> optionalShift = Optional.of(repository.save(shift));

        return optionalShift.map(this::shiftMapper);
    }

    public Optional<ShiftDTO> update(Integer id, Shift updated) {
        Optional<Shift> shiftOptional = repository.findById(id);

        if (shiftOptional.isPresent()) {
            return Optional.of(shiftMapper(repository.save(updated)));
        }

        return Optional.empty();
    }

    public boolean delete(Integer id) {
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

    public ShiftDTO createShiftForProvider(ShiftDTO dto) {
        if (existsShiftAtSameTime(dto.getProviderId(), dto.getDateTime())) {
            throw new RuntimeException("Oops! There are another shift for that provider at that time");
        }

        Provider provider = providerRepository.findById(dto.getProviderId())
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        Shift shift = new Shift();

        shift.setDateTime(dto.getDateTime());
        shift.setAvailable(true);
        shift.setProvider(provider);

        return shiftMapper(repository.save(shift));
    }

    public List<ShiftDTO> getAvailableByProvider(Integer providerId) {
        return repository.findByProviderIdAndAvailableTrue(providerId).stream().map(this::shiftMapper).toList();
    }

    public boolean existsShiftAtSameTime(Integer providerId, LocalDateTime dateTime) {
        return repository.existsByProviderIdAndDateTime(providerId, dateTime);
    }

    public ShiftDTO shiftMapper(Shift shift) {
        ShiftDTO dto = new ShiftDTO();

        dto.setProviderId(shift.getProvider().getId());
        dto.setDateTime(shift.getDateTime());
        dto.setAvailable(shift.isAvailable());

        return dto;
    }
}
