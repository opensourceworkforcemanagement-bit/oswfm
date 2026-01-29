package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.CodeTypes;
import org.oswfm.timesheetservice.model.dto.CodeTypesRequestDTO;
import org.oswfm.timesheetservice.model.dto.CodeTypesResponseDTO;
import org.oswfm.timesheetservice.repository.CodeTypesRepository;
import org.oswfm.timesheetservice.model.mapper.CodeTypesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CodeTypesService {

    @Autowired
    private CodeTypesRepository repository;

    @Autowired
    private CodeTypesMapper mapper;

    /**
     * Create a new CodeTypes
     */
    public CodeTypesResponseDTO create(CodeTypesRequestDTO requestDTO) {
        CodeTypes entity = mapper.toEntity(requestDTO);
        CodeTypes saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get CodeTypes by ID
     */
    @Transactional(readOnly = true)
    public Optional<CodeTypesResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get CodeTypes by name
     */
    @Transactional(readOnly = true)
    public Optional<CodeTypesResponseDTO> getByName(String codeTypeName) {
        return repository.findByCodeTypeName(codeTypeName)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all CodeTypes
     */
    @Transactional(readOnly = true)
    public List<CodeTypesResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing CodeTypes
     */
    public Optional<CodeTypesResponseDTO> update(Integer id, CodeTypesRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    CodeTypes updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete CodeTypes by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if CodeTypes exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Check if CodeTypes exists by name
     */
    @Transactional(readOnly = true)
    public boolean existsByName(String codeTypeName) {
        return repository.existsByCodeTypeName(codeTypeName);
    }

    /**
     * Get count of all CodeTypes
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
