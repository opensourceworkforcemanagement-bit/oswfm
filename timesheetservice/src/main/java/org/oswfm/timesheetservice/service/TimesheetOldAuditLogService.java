package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetOldAuditLog;
import org.oswfm.timesheetservice.model.dto.TimesheetOldAuditLogRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetOldAuditLogResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetOldAuditLogRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetOldAuditLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetOldAuditLogService {

    @Autowired
    private TimesheetOldAuditLogRepository repository;

    @Autowired
    private TimesheetOldAuditLogMapper mapper;

    /**
     * Create a new TimesheetOldAuditLog
     */
    public TimesheetOldAuditLogResponseDTO create(TimesheetOldAuditLogRequestDTO requestDTO) {
        TimesheetOldAuditLog entity = mapper.toEntity(requestDTO);
        TimesheetOldAuditLog saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetOldAuditLog by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetOldAuditLogResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetOldAuditLogs
     */
    @Transactional(readOnly = true)
    public List<TimesheetOldAuditLogResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetOldAuditLog
     */
    public Optional<TimesheetOldAuditLogResponseDTO> update(Integer id, TimesheetOldAuditLogRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetOldAuditLog updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetOldAuditLog by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetOldAuditLog exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetOldAuditLogs
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
