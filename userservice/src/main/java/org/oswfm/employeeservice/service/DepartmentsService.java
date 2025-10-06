package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.Departments;
import org.oswfm.employeeservice.model.dto.DepartmentsRequestDTO;
import org.oswfm.employeeservice.model.dto.DepartmentsResponseDTO;
import org.oswfm.employeeservice.repository.DepartmentsRepository;
import org.oswfm.employeeservice.model.mapper.DepartmentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentsService {

    @Autowired
    private DepartmentsRepository repository;

    @Autowired
    private DepartmentsMapper mapper;

    /**
     * Create a new Departments
     */
    public DepartmentsResponseDTO create(DepartmentsRequestDTO requestDTO) {
        Departments entity = mapper.toEntity(requestDTO);
        Departments saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get Departments by ID
     */
    @Transactional(readOnly = true)
    public Optional<DepartmentsResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all Departmentss
     */
    @Transactional(readOnly = true)
    public List<DepartmentsResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing Departments
     */
    public Optional<DepartmentsResponseDTO> update(Integer id, DepartmentsRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    Departments updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete Departments by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if Departments exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all Departmentss
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
