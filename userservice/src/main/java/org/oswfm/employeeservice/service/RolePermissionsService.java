package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.RolePermissions;
import org.oswfm.employeeservice.model.dto.RolePermissionsRequestDTO;
import org.oswfm.employeeservice.model.dto.RolePermissionsResponseDTO;
import org.oswfm.employeeservice.repository.RolePermissionsRepository;
import org.oswfm.employeeservice.model.mapper.RolePermissionsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RolePermissionsService {

    @Autowired
    private RolePermissionsRepository repository;

    @Autowired
    private RolePermissionsMapper mapper;

    /**
     * Create a new RolePermissions
     */
    public RolePermissionsResponseDTO create(RolePermissionsRequestDTO requestDTO) {
        RolePermissions entity = mapper.toEntity(requestDTO);
        RolePermissions saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get RolePermissions by ID
     */
    @Transactional(readOnly = true)
    public Optional<RolePermissionsResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all RolePermissionss
     */
    @Transactional(readOnly = true)
    public List<RolePermissionsResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing RolePermissions
     */
    public Optional<RolePermissionsResponseDTO> update(Integer id, RolePermissionsRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    RolePermissions updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete RolePermissions by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if RolePermissions exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all RolePermissionss
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
