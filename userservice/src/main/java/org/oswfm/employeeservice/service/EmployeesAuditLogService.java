package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.EmployeesAuditLog;
import org.oswfm.employeeservice.model.dto.EmployeesAuditLogRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesAuditLogResponseDTO;
import org.oswfm.employeeservice.repository.EmployeesAuditLogRepository;
import org.oswfm.employeeservice.model.mapper.EmployeesAuditLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeesAuditLogService {

    @Autowired
    private EmployeesAuditLogRepository repository;

    @Autowired
    private EmployeesAuditLogMapper mapper;

    /**
     * Create a new EmployeesAuditLog
     */
    public EmployeesAuditLogResponseDTO create(EmployeesAuditLogRequestDTO requestDTO) {
        EmployeesAuditLog entity = mapper.toEntity(requestDTO);
        EmployeesAuditLog saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get EmployeesAuditLog by ID
     */
    @Transactional(readOnly = true)
    public Optional<EmployeesAuditLogResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all EmployeesAuditLogs
     */
    @Transactional(readOnly = true)
    public List<EmployeesAuditLogResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing EmployeesAuditLog
     */
    public Optional<EmployeesAuditLogResponseDTO> update(Integer id, EmployeesAuditLogRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    EmployeesAuditLog updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete EmployeesAuditLog by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if EmployeesAuditLog exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all EmployeesAuditLogs
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
