package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.Employees;
import org.oswfm.employeeservice.model.dto.EmployeesRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesResponseDTO;
import org.oswfm.employeeservice.repository.EmployeesRepository;
import org.oswfm.employeeservice.model.mapper.EmployeesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeesService {

    @Autowired
    private EmployeesRepository repository;

    @Autowired
    private EmployeesMapper mapper;

    /**
     * Create a new Employees
     */
    public EmployeesResponseDTO create(EmployeesRequestDTO requestDTO) {
        Employees entity = mapper.toEntity(requestDTO);
        Employees saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get Employees by ID
     */
    @Transactional(readOnly = true)
    public Optional<EmployeesResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all Employeess
     */
    @Transactional(readOnly = true)
    public List<EmployeesResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing Employees
     */
    public Optional<EmployeesResponseDTO> update(Integer id, EmployeesRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    Employees updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete Employees by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if Employees exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all Employeess
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
