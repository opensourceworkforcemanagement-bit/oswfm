package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.EmployeesStatusHistory;
import org.oswfm.employeeservice.model.dto.EmployeesStatusHistoryRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesStatusHistoryResponseDTO;
import org.oswfm.employeeservice.repository.EmployeesStatusHistoryRepository;
import org.oswfm.employeeservice.model.mapper.EmployeesStatusHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeesStatusHistoryService {

    @Autowired
    private EmployeesStatusHistoryRepository repository;

    @Autowired
    private EmployeesStatusHistoryMapper mapper;

    /**
     * Create a new EmployeesStatusHistory
     */
    public EmployeesStatusHistoryResponseDTO create(EmployeesStatusHistoryRequestDTO requestDTO) {
        EmployeesStatusHistory entity = mapper.toEntity(requestDTO);
        EmployeesStatusHistory saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get EmployeesStatusHistory by ID
     */
    @Transactional(readOnly = true)
    public Optional<EmployeesStatusHistoryResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all EmployeesStatusHistorys
     */
    @Transactional(readOnly = true)
    public List<EmployeesStatusHistoryResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing EmployeesStatusHistory
     */
    public Optional<EmployeesStatusHistoryResponseDTO> update(Integer id, EmployeesStatusHistoryRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    EmployeesStatusHistory updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete EmployeesStatusHistory by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if EmployeesStatusHistory exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all EmployeesStatusHistorys
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
