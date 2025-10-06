package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesOld;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesOldRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesOldResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetEntriesOldRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetEntriesOldMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetEntriesOldService {

    @Autowired
    private TimesheetEntriesOldRepository repository;

    @Autowired
    private TimesheetEntriesOldMapper mapper;

    /**
     * Create a new TimesheetEntriesOld
     */
    public TimesheetEntriesOldResponseDTO create(TimesheetEntriesOldRequestDTO requestDTO) {
        TimesheetEntriesOld entity = mapper.toEntity(requestDTO);
        TimesheetEntriesOld saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetEntriesOld by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetEntriesOldResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetEntriesOlds
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntriesOldResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetEntriesOld
     */
    public Optional<TimesheetEntriesOldResponseDTO> update(Integer id, TimesheetEntriesOldRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetEntriesOld updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetEntriesOld by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetEntriesOld exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetEntriesOlds
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
