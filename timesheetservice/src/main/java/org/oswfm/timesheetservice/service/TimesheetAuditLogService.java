package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetAuditLog;
import org.oswfm.timesheetservice.model.dto.TimesheetAuditLogRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetAuditLogResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetAuditLogRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetAuditLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetAuditLogService {

    @Autowired
    private TimesheetAuditLogRepository repository;

    @Autowired
    private TimesheetAuditLogMapper mapper;

    /**
     * Create a new TimesheetAuditLog
     */
    public TimesheetAuditLogResponseDTO create(TimesheetAuditLogRequestDTO requestDTO) {
        TimesheetAuditLog entity = mapper.toEntity(requestDTO);
        TimesheetAuditLog saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetAuditLog by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetAuditLogResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetAuditLogs
     */
    @Transactional(readOnly = true)
    public List<TimesheetAuditLogResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetAuditLog
     */
    public Optional<TimesheetAuditLogResponseDTO> update(Integer id, TimesheetAuditLogRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetAuditLog updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetAuditLog by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetAuditLog exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetAuditLogs
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
