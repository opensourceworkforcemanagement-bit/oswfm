package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.EmployeesRoles;
import org.oswfm.employeeservice.model.dto.EmployeesRolesRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesRolesResponseDTO;
import org.oswfm.employeeservice.repository.EmployeesRolesRepository;
import org.oswfm.employeeservice.model.mapper.EmployeesRolesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeesRolesService {

    @Autowired
    private EmployeesRolesRepository repository;

    @Autowired
    private EmployeesRolesMapper mapper;

    /**
     * Create a new EmployeesRoles
     */
    public EmployeesRolesResponseDTO create(EmployeesRolesRequestDTO requestDTO) {
        EmployeesRoles entity = mapper.toEntity(requestDTO);
        EmployeesRoles saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get EmployeesRoles by ID
     */
    @Transactional(readOnly = true)
    public Optional<EmployeesRolesResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all EmployeesRoless
     */
    @Transactional(readOnly = true)
    public List<EmployeesRolesResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing EmployeesRoles
     */
    public Optional<EmployeesRolesResponseDTO> update(Integer id, EmployeesRolesRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    EmployeesRoles updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete EmployeesRoles by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if EmployeesRoles exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all EmployeesRoless
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
