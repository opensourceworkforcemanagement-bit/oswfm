package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.PayPeriods;
import org.oswfm.timesheetservice.model.dto.PayPeriodsRequestDTO;
import org.oswfm.timesheetservice.model.dto.PayPeriodsResponseDTO;
import org.oswfm.timesheetservice.repository.PayPeriodsRepository;
import org.oswfm.timesheetservice.model.mapper.PayPeriodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PayPeriodsService {

    @Autowired
    private PayPeriodsRepository repository;

    @Autowired
    private PayPeriodsMapper mapper;

    /**
     * Create a new PayPeriods
     */
    public PayPeriodsResponseDTO create(PayPeriodsRequestDTO requestDTO) {
        PayPeriods entity = mapper.toEntity(requestDTO);
        PayPeriods saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get PayPeriods by ID
     */
    @Transactional(readOnly = true)
    public Optional<PayPeriodsResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all PayPeriodss
     */
    @Transactional(readOnly = true)
    public List<PayPeriodsResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing PayPeriods
     */
    public Optional<PayPeriodsResponseDTO> update(Integer id, PayPeriodsRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    PayPeriods updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete PayPeriods by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if PayPeriods exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all PayPeriodss
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
