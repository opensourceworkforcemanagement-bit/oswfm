package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.EmployeesEmergencyContactPhoneNumbers;
import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactPhoneNumbersRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactPhoneNumbersResponseDTO;
import org.oswfm.employeeservice.repository.EmployeesEmergencyContactPhoneNumbersRepository;
import org.oswfm.employeeservice.model.mapper.EmployeesEmergencyContactPhoneNumbersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeesEmergencyContactPhoneNumbersService {

    @Autowired
    private EmployeesEmergencyContactPhoneNumbersRepository repository;

    @Autowired
    private EmployeesEmergencyContactPhoneNumbersMapper mapper;

    /**
     * Create a new EmployeesEmergencyContactPhoneNumbers
     */
    public EmployeesEmergencyContactPhoneNumbersResponseDTO create(EmployeesEmergencyContactPhoneNumbersRequestDTO requestDTO) {
        EmployeesEmergencyContactPhoneNumbers entity = mapper.toEntity(requestDTO);
        EmployeesEmergencyContactPhoneNumbers saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get EmployeesEmergencyContactPhoneNumbers by ID
     */
    @Transactional(readOnly = true)
    public Optional<EmployeesEmergencyContactPhoneNumbersResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all EmployeesEmergencyContactPhoneNumberss
     */
    @Transactional(readOnly = true)
    public List<EmployeesEmergencyContactPhoneNumbersResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing EmployeesEmergencyContactPhoneNumbers
     */
    public Optional<EmployeesEmergencyContactPhoneNumbersResponseDTO> update(Integer id, EmployeesEmergencyContactPhoneNumbersRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    EmployeesEmergencyContactPhoneNumbers updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete EmployeesEmergencyContactPhoneNumbers by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if EmployeesEmergencyContactPhoneNumbers exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all EmployeesEmergencyContactPhoneNumberss
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
