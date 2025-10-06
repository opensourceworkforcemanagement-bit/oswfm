package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.Timesheet;
import org.oswfm.timesheetservice.model.dto.TimesheetRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetService {

    @Autowired
    private TimesheetRepository repository;

    @Autowired
    private TimesheetMapper mapper;

    /**
     * Create a new Timesheet
     */
    public TimesheetResponseDTO create(TimesheetRequestDTO requestDTO) {
        Timesheet entity = mapper.toEntity(requestDTO);
        Timesheet saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get Timesheet by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all Timesheets
     */
    @Transactional(readOnly = true)
    public List<TimesheetResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing Timesheet
     */
    public Optional<TimesheetResponseDTO> update(Integer id, TimesheetRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    Timesheet updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete Timesheet by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if Timesheet exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all Timesheets
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
