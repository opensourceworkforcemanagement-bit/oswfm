package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesInOutOld;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesInOutOldRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesInOutOldResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetEntriesInOutOldRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetEntriesInOutOldMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetEntriesInOutOldService {

    @Autowired
    private TimesheetEntriesInOutOldRepository repository;

    @Autowired
    private TimesheetEntriesInOutOldMapper mapper;

    /**
     * Create a new TimesheetEntriesInOutOld
     */
    public TimesheetEntriesInOutOldResponseDTO create(TimesheetEntriesInOutOldRequestDTO requestDTO) {
        TimesheetEntriesInOutOld entity = mapper.toEntity(requestDTO);
        TimesheetEntriesInOutOld saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetEntriesInOutOld by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetEntriesInOutOldResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetEntriesInOutOlds
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntriesInOutOldResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetEntriesInOutOld
     */
    public Optional<TimesheetEntriesInOutOldResponseDTO> update(Integer id, TimesheetEntriesInOutOldRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetEntriesInOutOld updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetEntriesInOutOld by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetEntriesInOutOld exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetEntriesInOutOlds
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
