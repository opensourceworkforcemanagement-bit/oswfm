package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.WorkCodes;
import org.oswfm.timesheetservice.model.dto.WorkCodesRequestDTO;
import org.oswfm.timesheetservice.model.dto.WorkCodesResponseDTO;
import org.oswfm.timesheetservice.repository.WorkCodesRepository;
import org.oswfm.timesheetservice.model.mapper.WorkCodesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkCodesService {

    @Autowired
    private WorkCodesRepository repository;

    @Autowired
    private WorkCodesMapper mapper;

    /**
     * Create a new WorkCodes
     */
    public WorkCodesResponseDTO create(WorkCodesRequestDTO requestDTO) {
        WorkCodes entity = mapper.toEntity(requestDTO);
        WorkCodes saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get WorkCodes by ID
     */
    @Transactional(readOnly = true)
    public Optional<WorkCodesResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all WorkCodess
     */
    @Transactional(readOnly = true)
    public List<WorkCodesResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing WorkCodes
     */
    public Optional<WorkCodesResponseDTO> update(Integer id, WorkCodesRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    WorkCodes updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete WorkCodes by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if WorkCodes exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all WorkCodess
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
