package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetRemarks;
import org.oswfm.timesheetservice.model.dto.TimesheetRemarksRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetRemarksResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetRemarksRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetRemarksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetRemarksService {

    @Autowired
    private TimesheetRemarksRepository repository;

    @Autowired
    private TimesheetRemarksMapper mapper;

    /**
     * Create a new TimesheetRemarks
     */
    public TimesheetRemarksResponseDTO create(TimesheetRemarksRequestDTO requestDTO) {
        TimesheetRemarks entity = mapper.toEntity(requestDTO);
        TimesheetRemarks saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetRemarks by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetRemarksResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetRemarkss
     */
    @Transactional(readOnly = true)
    public List<TimesheetRemarksResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetRemarks
     */
    public Optional<TimesheetRemarksResponseDTO> update(Integer id, TimesheetRemarksRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetRemarks updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetRemarks by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetRemarks exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetRemarkss
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
