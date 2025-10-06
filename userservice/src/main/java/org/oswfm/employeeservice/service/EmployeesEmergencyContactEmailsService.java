package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.EmployeesEmergencyContactEmails;
import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactEmailsRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactEmailsResponseDTO;
import org.oswfm.employeeservice.repository.EmployeesEmergencyContactEmailsRepository;
import org.oswfm.employeeservice.model.mapper.EmployeesEmergencyContactEmailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeesEmergencyContactEmailsService {

    @Autowired
    private EmployeesEmergencyContactEmailsRepository repository;

    @Autowired
    private EmployeesEmergencyContactEmailsMapper mapper;

    /**
     * Create a new EmployeesEmergencyContactEmails
     */
    public EmployeesEmergencyContactEmailsResponseDTO create(EmployeesEmergencyContactEmailsRequestDTO requestDTO) {
        EmployeesEmergencyContactEmails entity = mapper.toEntity(requestDTO);
        EmployeesEmergencyContactEmails saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get EmployeesEmergencyContactEmails by ID
     */
    @Transactional(readOnly = true)
    public Optional<EmployeesEmergencyContactEmailsResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all EmployeesEmergencyContactEmailss
     */
    @Transactional(readOnly = true)
    public List<EmployeesEmergencyContactEmailsResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing EmployeesEmergencyContactEmails
     */
    public Optional<EmployeesEmergencyContactEmailsResponseDTO> update(Integer id, EmployeesEmergencyContactEmailsRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    EmployeesEmergencyContactEmails updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete EmployeesEmergencyContactEmails by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if EmployeesEmergencyContactEmails exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all EmployeesEmergencyContactEmailss
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
