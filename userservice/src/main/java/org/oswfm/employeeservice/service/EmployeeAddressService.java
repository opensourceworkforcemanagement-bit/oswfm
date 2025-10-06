package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.EmployeeAddress;
import org.oswfm.employeeservice.model.dto.EmployeeAddressRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeeAddressResponseDTO;
import org.oswfm.employeeservice.repository.EmployeeAddressRepository;
import org.oswfm.employeeservice.model.mapper.EmployeeAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeAddressService {

    @Autowired
    private EmployeeAddressRepository repository;

    @Autowired
    private EmployeeAddressMapper mapper;

    /**
     * Create a new EmployeeAddress
     */
    public EmployeeAddressResponseDTO create(EmployeeAddressRequestDTO requestDTO) {
        EmployeeAddress entity = mapper.toEntity(requestDTO);
        EmployeeAddress saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get EmployeeAddress by ID
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeAddressResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all EmployeeAddresss
     */
    @Transactional(readOnly = true)
    public List<EmployeeAddressResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing EmployeeAddress
     */
    public Optional<EmployeeAddressResponseDTO> update(Integer id, EmployeeAddressRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    EmployeeAddress updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete EmployeeAddress by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if EmployeeAddress exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all EmployeeAddresss
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
