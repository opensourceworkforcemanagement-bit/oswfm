package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.EmployeesSettings;
import org.oswfm.employeeservice.model.dto.EmployeesSettingsRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesSettingsResponseDTO;
import org.oswfm.employeeservice.repository.EmployeesSettingsRepository;
import org.oswfm.employeeservice.model.mapper.EmployeesSettingsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeesSettingsService {

    @Autowired
    private EmployeesSettingsRepository repository;

    @Autowired
    private EmployeesSettingsMapper mapper;

    /**
     * Create a new EmployeesSettings
     */
    public EmployeesSettingsResponseDTO create(EmployeesSettingsRequestDTO requestDTO) {
        EmployeesSettings entity = mapper.toEntity(requestDTO);
        EmployeesSettings saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get EmployeesSettings by ID
     */
    @Transactional(readOnly = true)
    public Optional<EmployeesSettingsResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all EmployeesSettingss
     */
    @Transactional(readOnly = true)
    public List<EmployeesSettingsResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing EmployeesSettings
     */
    public Optional<EmployeesSettingsResponseDTO> update(Integer id, EmployeesSettingsRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    EmployeesSettings updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete EmployeesSettings by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if EmployeesSettings exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all EmployeesSettingss
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
