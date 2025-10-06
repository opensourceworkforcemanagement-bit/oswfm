package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesInOut;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesInOutRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesInOutResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetEntriesInOutRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetEntriesInOutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetEntriesInOutService {

    @Autowired
    private TimesheetEntriesInOutRepository repository;

    @Autowired
    private TimesheetEntriesInOutMapper mapper;

    /**
     * Create a new TimesheetEntriesInOut
     */
    public TimesheetEntriesInOutResponseDTO create(TimesheetEntriesInOutRequestDTO requestDTO) {
        TimesheetEntriesInOut entity = mapper.toEntity(requestDTO);
        TimesheetEntriesInOut saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetEntriesInOut by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetEntriesInOutResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetEntriesInOuts
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntriesInOutResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetEntriesInOut
     */
    public Optional<TimesheetEntriesInOutResponseDTO> update(Integer id, TimesheetEntriesInOutRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetEntriesInOut updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetEntriesInOut by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetEntriesInOut exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetEntriesInOuts
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
