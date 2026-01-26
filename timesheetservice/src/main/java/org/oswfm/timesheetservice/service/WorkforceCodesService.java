package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.WorkforceCodes;
import org.oswfm.timesheetservice.model.dto.WorkforceCodesRequestDTO;
import org.oswfm.timesheetservice.model.dto.WorkforceCodesResponseDTO;
import org.oswfm.timesheetservice.repository.WorkforceCodesRepository;
import org.oswfm.timesheetservice.model.mapper.WorkforceCodesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkforceCodesService {

    @Autowired
    private WorkforceCodesRepository repository;

    @Autowired
    private WorkforceCodesMapper mapper;

    /**
     * Create a new WorkforceCodes
     */
    public WorkforceCodesResponseDTO create(WorkforceCodesRequestDTO requestDTO) {
        WorkforceCodes entity = mapper.toEntity(requestDTO);
        WorkforceCodes saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get WorkforceCodes by ID
     */
    @Transactional(readOnly = true)
    public Optional<WorkforceCodesResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all WorkforceCodes
     */
    @Transactional(readOnly = true)
    public List<WorkforceCodesResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get WorkforceCodes by code type ID
     */
    @Transactional(readOnly = true)
    public List<WorkforceCodesResponseDTO> getByCodeTypeId(Integer codeTypeId) {
        return repository.findByCodeTypeId(codeTypeId).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get WorkforceCodes by status
     */
    @Transactional(readOnly = true)
    public List<WorkforceCodesResponseDTO> getByStatus(Integer status) {
        return repository.findByStatus(status).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get WorkforceCodes by code type ID and status
     */
    @Transactional(readOnly = true)
    public List<WorkforceCodesResponseDTO> getByCodeTypeIdAndStatus(Integer codeTypeId, Integer status) {
        return repository.findByCodeTypeIdAndStatus(codeTypeId, status).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing WorkforceCodes
     */
    public Optional<WorkforceCodesResponseDTO> update(Integer id, WorkforceCodesRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    WorkforceCodes updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete WorkforceCodes by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if WorkforceCodes exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all WorkforceCodes
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
