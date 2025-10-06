package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.EmailAddresses;
import org.oswfm.employeeservice.model.dto.EmailAddressesRequestDTO;
import org.oswfm.employeeservice.model.dto.EmailAddressesResponseDTO;
import org.oswfm.employeeservice.repository.EmailAddressesRepository;
import org.oswfm.employeeservice.model.mapper.EmailAddressesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmailAddressesService {

    @Autowired
    private EmailAddressesRepository repository;

    @Autowired
    private EmailAddressesMapper mapper;

    /**
     * Create a new EmailAddresses
     */
    public EmailAddressesResponseDTO create(EmailAddressesRequestDTO requestDTO) {
        EmailAddresses entity = mapper.toEntity(requestDTO);
        EmailAddresses saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get EmailAddresses by ID
     */
    @Transactional(readOnly = true)
    public Optional<EmailAddressesResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all EmailAddressess
     */
    @Transactional(readOnly = true)
    public List<EmailAddressesResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing EmailAddresses
     */
    public Optional<EmailAddressesResponseDTO> update(Integer id, EmailAddressesRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    EmailAddresses updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete EmailAddresses by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if EmailAddresses exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all EmailAddressess
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
