package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetOperationsTypes;
import org.oswfm.timesheetservice.model.dto.TimesheetOperationsTypesRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetOperationsTypesResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetOperationsTypesRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetOperationsTypesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetOperationsTypesService {

    @Autowired
    private TimesheetOperationsTypesRepository repository;

    @Autowired
    private TimesheetOperationsTypesMapper mapper;

    /**
     * Create a new TimesheetOperationsTypes
     */
    public TimesheetOperationsTypesResponseDTO create(TimesheetOperationsTypesRequestDTO requestDTO) {
        TimesheetOperationsTypes entity = mapper.toEntity(requestDTO);
        TimesheetOperationsTypes saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetOperationsTypes by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetOperationsTypesResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetOperationsTypess
     */
    @Transactional(readOnly = true)
    public List<TimesheetOperationsTypesResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetOperationsTypes
     */
    public Optional<TimesheetOperationsTypesResponseDTO> update(Integer id, TimesheetOperationsTypesRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetOperationsTypes updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetOperationsTypes by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetOperationsTypes exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetOperationsTypess
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
