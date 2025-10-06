package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetApprovalsArchive;
import org.oswfm.timesheetservice.model.dto.TimesheetApprovalsArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetApprovalsArchiveResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetApprovalsArchiveRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetApprovalsArchiveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetApprovalsArchiveService {

    @Autowired
    private TimesheetApprovalsArchiveRepository repository;

    @Autowired
    private TimesheetApprovalsArchiveMapper mapper;

    /**
     * Create a new TimesheetApprovalsArchive
     */
    public TimesheetApprovalsArchiveResponseDTO create(TimesheetApprovalsArchiveRequestDTO requestDTO) {
        TimesheetApprovalsArchive entity = mapper.toEntity(requestDTO);
        TimesheetApprovalsArchive saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetApprovalsArchive by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetApprovalsArchiveResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetApprovalsArchives
     */
    @Transactional(readOnly = true)
    public List<TimesheetApprovalsArchiveResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetApprovalsArchive
     */
    public Optional<TimesheetApprovalsArchiveResponseDTO> update(Integer id, TimesheetApprovalsArchiveRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetApprovalsArchive updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetApprovalsArchive by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetApprovalsArchive exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetApprovalsArchives
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
