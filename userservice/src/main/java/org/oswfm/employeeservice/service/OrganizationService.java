package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.Organization;
import org.oswfm.employeeservice.model.dto.OrganizationRequestDTO;
import org.oswfm.employeeservice.model.dto.OrganizationResponseDTO;
import org.oswfm.employeeservice.repository.OrganizationRepository;
import org.oswfm.employeeservice.model.mapper.OrganizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrganizationService {

    @Autowired
    private OrganizationRepository repository;

    @Autowired
    private OrganizationMapper mapper;

    /**
     * Create a new Organization
     */
    public OrganizationResponseDTO create(OrganizationRequestDTO requestDTO) {
        Organization entity = mapper.toEntity(requestDTO);
        Organization saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get Organization by ID
     */
    @Transactional(readOnly = true)
    public Optional<OrganizationResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all Organizations
     */
    @Transactional(readOnly = true)
    public List<OrganizationResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing Organization
     */
    public Optional<OrganizationResponseDTO> update(Integer id, OrganizationRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    Organization updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete Organization by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if Organization exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all Organizations
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
