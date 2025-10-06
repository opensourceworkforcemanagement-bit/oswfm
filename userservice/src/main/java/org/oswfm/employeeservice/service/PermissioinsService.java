package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.Permissioins;
import org.oswfm.employeeservice.model.dto.PermissioinsRequestDTO;
import org.oswfm.employeeservice.model.dto.PermissioinsResponseDTO;
import org.oswfm.employeeservice.repository.PermissioinsRepository;
import org.oswfm.employeeservice.model.mapper.PermissioinsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PermissioinsService {

    @Autowired
    private PermissioinsRepository repository;

    @Autowired
    private PermissioinsMapper mapper;

    /**
     * Create a new Permissioins
     */
    public PermissioinsResponseDTO create(PermissioinsRequestDTO requestDTO) {
        Permissioins entity = mapper.toEntity(requestDTO);
        Permissioins saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get Permissioins by ID
     */
    @Transactional(readOnly = true)
    public Optional<PermissioinsResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all Permissioinss
     */
    @Transactional(readOnly = true)
    public List<PermissioinsResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing Permissioins
     */
    public Optional<PermissioinsResponseDTO> update(Integer id, PermissioinsRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    Permissioins updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete Permissioins by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if Permissioins exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all Permissioinss
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
