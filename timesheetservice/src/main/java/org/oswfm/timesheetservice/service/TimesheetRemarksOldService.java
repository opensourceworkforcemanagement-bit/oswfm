package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetRemarksOld;
import org.oswfm.timesheetservice.model.dto.TimesheetRemarksOldRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetRemarksOldResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetRemarksOldRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetRemarksOldMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetRemarksOldService {

    @Autowired
    private TimesheetRemarksOldRepository repository;

    @Autowired
    private TimesheetRemarksOldMapper mapper;

    /**
     * Create a new TimesheetRemarksOld
     */
    public TimesheetRemarksOldResponseDTO create(TimesheetRemarksOldRequestDTO requestDTO) {
        TimesheetRemarksOld entity = mapper.toEntity(requestDTO);
        TimesheetRemarksOld saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetRemarksOld by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetRemarksOldResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetRemarksOlds
     */
    @Transactional(readOnly = true)
    public List<TimesheetRemarksOldResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetRemarksOld
     */
    public Optional<TimesheetRemarksOldResponseDTO> update(Integer id, TimesheetRemarksOldRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetRemarksOld updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetRemarksOld by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetRemarksOld exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetRemarksOlds
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
