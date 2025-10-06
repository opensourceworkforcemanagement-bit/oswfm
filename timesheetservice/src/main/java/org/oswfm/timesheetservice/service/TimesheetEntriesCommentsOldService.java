package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesCommentsOld;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsOldRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsOldResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetEntriesCommentsOldRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetEntriesCommentsOldMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetEntriesCommentsOldService {

    @Autowired
    private TimesheetEntriesCommentsOldRepository repository;

    @Autowired
    private TimesheetEntriesCommentsOldMapper mapper;

    /**
     * Create a new TimesheetEntriesCommentsOld
     */
    public TimesheetEntriesCommentsOldResponseDTO create(TimesheetEntriesCommentsOldRequestDTO requestDTO) {
        TimesheetEntriesCommentsOld entity = mapper.toEntity(requestDTO);
        TimesheetEntriesCommentsOld saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetEntriesCommentsOld by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetEntriesCommentsOldResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetEntriesCommentsOlds
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntriesCommentsOldResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetEntriesCommentsOld
     */
    public Optional<TimesheetEntriesCommentsOldResponseDTO> update(Integer id, TimesheetEntriesCommentsOldRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetEntriesCommentsOld updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetEntriesCommentsOld by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetEntriesCommentsOld exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetEntriesCommentsOlds
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
