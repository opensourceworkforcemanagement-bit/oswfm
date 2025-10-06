package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.EmployeesPreferences;
import org.oswfm.employeeservice.model.dto.EmployeesPreferencesRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesPreferencesResponseDTO;
import org.oswfm.employeeservice.repository.EmployeesPreferencesRepository;
import org.oswfm.employeeservice.model.mapper.EmployeesPreferencesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeesPreferencesService {

    @Autowired
    private EmployeesPreferencesRepository repository;

    @Autowired
    private EmployeesPreferencesMapper mapper;

    /**
     * Create a new EmployeesPreferences
     */
    public EmployeesPreferencesResponseDTO create(EmployeesPreferencesRequestDTO requestDTO) {
        EmployeesPreferences entity = mapper.toEntity(requestDTO);
        EmployeesPreferences saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get EmployeesPreferences by ID
     */
    @Transactional(readOnly = true)
    public Optional<EmployeesPreferencesResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all EmployeesPreferencess
     */
    @Transactional(readOnly = true)
    public List<EmployeesPreferencesResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing EmployeesPreferences
     */
    public Optional<EmployeesPreferencesResponseDTO> update(Integer id, EmployeesPreferencesRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    EmployeesPreferences updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete EmployeesPreferences by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if EmployeesPreferences exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all EmployeesPreferencess
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
