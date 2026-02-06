package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.EmployeeUser;
import org.oswfm.employeeservice.model.dto.EmployeeUserRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeeUserResponseDTO;
import org.oswfm.employeeservice.repository.EmployeeUserRepository;
import org.oswfm.employeeservice.model.mapper.EmployeeUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeUserService {

    @Autowired
    private EmployeeUserRepository repository;

    @Autowired
    private EmployeeUserMapper mapper;

    /**
     * Create a new EmployeeUser
     */
    public EmployeeUserResponseDTO create(EmployeeUserRequestDTO requestDTO) {
        EmployeeUser entity = mapper.toEntity(requestDTO);
        EmployeeUser saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get EmployeeUser by ID
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeUserResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all EmployeeUsers
     */
    @Transactional(readOnly = true)
    public List<EmployeeUserResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get EmployeeUsers by employee ID
     */
    @Transactional(readOnly = true)
    public List<EmployeeUserResponseDTO> getByEmployeeId(Integer employeeId) {
        return repository.findByEmployeeId(employeeId).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get EmployeeUsers by user ID
     */
    @Transactional(readOnly = true)
    public List<EmployeeUserResponseDTO> getByUserId(Integer userId) {
        return repository.findByUserId(userId).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing EmployeeUser
     */
    public Optional<EmployeeUserResponseDTO> update(Integer id, EmployeeUserRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    EmployeeUser updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete EmployeeUser by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if EmployeeUser exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all EmployeeUsers
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
