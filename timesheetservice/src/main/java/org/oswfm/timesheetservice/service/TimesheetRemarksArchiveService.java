package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetRemarksArchive;
import org.oswfm.timesheetservice.model.dto.TimesheetRemarksArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetRemarksArchiveResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetRemarksArchiveRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetRemarksArchiveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetRemarksArchiveService {

    @Autowired
    private TimesheetRemarksArchiveRepository repository;

    @Autowired
    private TimesheetRemarksArchiveMapper mapper;

    /**
     * Create a new TimesheetRemarksArchive
     */
    public TimesheetRemarksArchiveResponseDTO create(TimesheetRemarksArchiveRequestDTO requestDTO) {
        TimesheetRemarksArchive entity = mapper.toEntity(requestDTO);
        TimesheetRemarksArchive saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetRemarksArchive by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetRemarksArchiveResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetRemarksArchives
     */
    @Transactional(readOnly = true)
    public List<TimesheetRemarksArchiveResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetRemarksArchive
     */
    public Optional<TimesheetRemarksArchiveResponseDTO> update(Integer id, TimesheetRemarksArchiveRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetRemarksArchive updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetRemarksArchive by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetRemarksArchive exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetRemarksArchives
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
