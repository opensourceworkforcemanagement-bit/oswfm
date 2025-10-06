package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetEntries;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetEntriesRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetEntriesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetEntriesService {

    @Autowired
    private TimesheetEntriesRepository repository;

    @Autowired
    private TimesheetEntriesMapper mapper;

    /**
     * Create a new TimesheetEntries
     */
    public TimesheetEntriesResponseDTO create(TimesheetEntriesRequestDTO requestDTO) {
        TimesheetEntries entity = mapper.toEntity(requestDTO);
        TimesheetEntries saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetEntries by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetEntriesResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetEntriess
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntriesResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetEntries
     */
    public Optional<TimesheetEntriesResponseDTO> update(Integer id, TimesheetEntriesRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetEntries updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetEntries by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetEntries exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetEntriess
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
