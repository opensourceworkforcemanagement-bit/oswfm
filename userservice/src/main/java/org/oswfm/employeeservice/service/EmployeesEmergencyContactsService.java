package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.EmployeesEmergencyContacts;
import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactsRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactsResponseDTO;
import org.oswfm.employeeservice.repository.EmployeesEmergencyContactsRepository;
import org.oswfm.employeeservice.model.mapper.EmployeesEmergencyContactsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeesEmergencyContactsService {

    @Autowired
    private EmployeesEmergencyContactsRepository repository;

    @Autowired
    private EmployeesEmergencyContactsMapper mapper;

    /**
     * Create a new EmployeesEmergencyContacts
     */
    public EmployeesEmergencyContactsResponseDTO create(EmployeesEmergencyContactsRequestDTO requestDTO) {
        EmployeesEmergencyContacts entity = mapper.toEntity(requestDTO);
        EmployeesEmergencyContacts saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get EmployeesEmergencyContacts by ID
     */
    @Transactional(readOnly = true)
    public Optional<EmployeesEmergencyContactsResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all EmployeesEmergencyContactss
     */
    @Transactional(readOnly = true)
    public List<EmployeesEmergencyContactsResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing EmployeesEmergencyContacts
     */
    public Optional<EmployeesEmergencyContactsResponseDTO> update(Integer id, EmployeesEmergencyContactsRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    EmployeesEmergencyContacts updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete EmployeesEmergencyContacts by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if EmployeesEmergencyContacts exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all EmployeesEmergencyContactss
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
