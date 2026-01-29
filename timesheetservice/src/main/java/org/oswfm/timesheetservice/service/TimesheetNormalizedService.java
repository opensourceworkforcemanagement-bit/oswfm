package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetNormalized;
import org.oswfm.timesheetservice.model.dto.TimesheetNormalizedRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetNormalizedResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetNormalizedRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetNormalizedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetNormalizedService {

    @Autowired
    private TimesheetNormalizedRepository repository;

    @Autowired
    private TimesheetNormalizedMapper mapper;

    /**
     * Create a new TimesheetNormalized
     */
    public TimesheetNormalizedResponseDTO create(TimesheetNormalizedRequestDTO requestDTO) {
        TimesheetNormalized entity = mapper.toEntity(requestDTO);
        TimesheetNormalized saved = repository.save(entity);
        return mapper.toResponseDTOWithoutEntries(saved);
    }

    /**
     * Get TimesheetNormalized by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetNormalizedResponseDTO> getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get TimesheetNormalized by ID without entries
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetNormalizedResponseDTO> getByIdWithoutEntries(Long id) {
        return repository.findById(id)
                .map(mapper::toResponseDTOWithoutEntries);
    }

    /**
     * Get all TimesheetNormalized
     */
    @Transactional(readOnly = true)
    public List<TimesheetNormalizedResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTOWithoutEntries)
                .collect(Collectors.toList());
    }

    /**
     * Get TimesheetNormalized by employee ID
     */
    @Transactional(readOnly = true)
    public List<TimesheetNormalizedResponseDTO> getByEmployeeId(Integer employeeId) {
        return repository.findByEmployeeId(employeeId).stream()
                .map(mapper::toResponseDTOWithoutEntries)
                .collect(Collectors.toList());
    }

    /**
     * Get TimesheetNormalized by pay period ID
     */
    @Transactional(readOnly = true)
    public List<TimesheetNormalizedResponseDTO> getByPayPeriodId(Integer payPeriodId) {
        return repository.findByPayPeriodId(payPeriodId).stream()
                .map(mapper::toResponseDTOWithoutEntries)
                .collect(Collectors.toList());
    }

    /**
     * Get TimesheetNormalized by status
     */
    @Transactional(readOnly = true)
    public List<TimesheetNormalizedResponseDTO> getByStatus(Integer status) {
        return repository.findByStatus(status).stream()
                .map(mapper::toResponseDTOWithoutEntries)
                .collect(Collectors.toList());
    }

    /**
     * Get TimesheetNormalized by employee ID and pay period ID
     */
    @Transactional(readOnly = true)
    public List<TimesheetNormalizedResponseDTO> getByEmployeeIdAndPayPeriodId(Integer employeeId, Integer payPeriodId) {
        return repository.findByEmployeeIdAndPayPeriodId(employeeId, payPeriodId).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get TimesheetNormalized by employee ID and status
     */
    @Transactional(readOnly = true)
    public List<TimesheetNormalizedResponseDTO> getByEmployeeIdAndStatus(Integer employeeId, Integer status) {
        return repository.findByEmployeeIdAndStatus(employeeId, status).stream()
                .map(mapper::toResponseDTOWithoutEntries)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetNormalized
     */
    public Optional<TimesheetNormalizedResponseDTO> update(Long id, TimesheetNormalizedRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetNormalized updated = repository.save(entity);
                    return mapper.toResponseDTOWithoutEntries(updated);
                });
    }

    /**
     * Update status only
     */
    public Optional<TimesheetNormalizedResponseDTO> updateStatus(Long id, Integer status) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setStatus(status);
                    TimesheetNormalized updated = repository.save(entity);
                    return mapper.toResponseDTOWithoutEntries(updated);
                });
    }

    /**
     * Delete TimesheetNormalized by ID
     */
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetNormalized exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetNormalized
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
