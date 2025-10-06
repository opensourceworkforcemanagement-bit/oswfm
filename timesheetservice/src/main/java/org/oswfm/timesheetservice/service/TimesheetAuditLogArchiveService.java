package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetAuditLogArchive;
import org.oswfm.timesheetservice.model.dto.TimesheetAuditLogArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetAuditLogArchiveResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetAuditLogArchiveRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetAuditLogArchiveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetAuditLogArchiveService {

    @Autowired
    private TimesheetAuditLogArchiveRepository repository;

    @Autowired
    private TimesheetAuditLogArchiveMapper mapper;

    /**
     * Create a new TimesheetAuditLogArchive
     */
    public TimesheetAuditLogArchiveResponseDTO create(TimesheetAuditLogArchiveRequestDTO requestDTO) {
        TimesheetAuditLogArchive entity = mapper.toEntity(requestDTO);
        TimesheetAuditLogArchive saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetAuditLogArchive by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetAuditLogArchiveResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetAuditLogArchives
     */
    @Transactional(readOnly = true)
    public List<TimesheetAuditLogArchiveResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetAuditLogArchive
     */
    public Optional<TimesheetAuditLogArchiveResponseDTO> update(Integer id, TimesheetAuditLogArchiveRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetAuditLogArchive updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetAuditLogArchive by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetAuditLogArchive exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetAuditLogArchives
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
