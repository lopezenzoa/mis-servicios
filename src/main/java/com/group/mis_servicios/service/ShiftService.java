package com.group.mis_servicios.service;

import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.entity.Shift;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.model.repository.ShiftRepository;
import com.group.mis_servicios.view.dto.ShiftDTO;
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

    public List<ShiftDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::shiftMapper)
                .toList();
    }
    public List<ShiftDTO> getAllByProvider(Long providerId) {
        return repository.findByProviderId(providerId)
                .stream()
                .map(this::shiftMapper)
                .toList();
    }


    public Optional<ShiftDTO> getById(Long id) {
        Optional<Shift> shift = repository.findById(id);

        return shift.map(this::shiftMapper);
    }

    public Optional<ShiftDTO> create(ShiftDTO shift) {
        Optional<Shift> optionalShift = Optional.of(repository.save(shiftMapper(shift)));

        return optionalShift.map(this::shiftMapper);
    }

    public Optional<ShiftDTO> update(Long id, ShiftDTO updated) {
        Optional<Shift> shiftOptional = repository.findById(id);

        if (shiftOptional.isPresent()
                && checkProvider(updated.getProviderId())
                && !existsShiftAtSameTime(updated.getProviderId(), LocalDateTime.parse(updated.getDateTime()))
        ) {
            Shift saved = repository.save(shiftMapper(updated));

            return Optional.of(shiftMapper(saved));
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

    public Shift shiftMapper(ShiftDTO dto) {
        Provider provider = providerRepository.findById(dto.getProviderId())
                .orElseThrow(() -> new RuntimeException("Provider not found with ID: " + dto.getProviderId()));

        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dto.getDateTime());
        } catch (Exception e) {
            throw new RuntimeException("Invalid date format: " + dto.getDateTime() + ". Use yyyy-MM-ddTHH:mm:ss");
        }

        Shift shift = new Shift();
        shift.setProvider(provider);
        shift.setAvailable(dto.isAvailable());
        shift.setDateTime(dateTime);

        return shift;
    }


    // checks if the provider exists given his id
    private boolean checkProvider(Long providerId) {
        return providerRepository.findAll()
                .stream()
                .anyMatch(p -> p.getId().equals(providerId));
    }
}
