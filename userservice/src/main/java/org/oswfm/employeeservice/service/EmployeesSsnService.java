package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.EmployeesSsn;
import org.oswfm.employeeservice.model.dto.EmployeesSsnRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesSsnResponseDTO;
import org.oswfm.employeeservice.repository.EmployeesSsnRepository;
import org.oswfm.employeeservice.model.mapper.EmployeesSsnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeesSsnService {

    @Autowired
    private EmployeesSsnRepository repository;

    @Autowired
    private EmployeesSsnMapper mapper;

    /**
     * Create a new EmployeesSsn
     */
    public EmployeesSsnResponseDTO create(EmployeesSsnRequestDTO requestDTO) {
        EmployeesSsn entity = mapper.toEntity(requestDTO);
        EmployeesSsn saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get EmployeesSsn by ID
     */
    @Transactional(readOnly = true)
    public Optional<EmployeesSsnResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all EmployeesSsns
     */
    @Transactional(readOnly = true)
    public List<EmployeesSsnResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing EmployeesSsn
     */
    public Optional<EmployeesSsnResponseDTO> update(Integer id, EmployeesSsnRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    EmployeesSsn updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete EmployeesSsn by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if EmployeesSsn exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all EmployeesSsns
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
