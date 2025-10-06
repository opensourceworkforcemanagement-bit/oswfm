package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.PhoneNumbers;
import org.oswfm.employeeservice.model.dto.PhoneNumbersRequestDTO;
import org.oswfm.employeeservice.model.dto.PhoneNumbersResponseDTO;
import org.oswfm.employeeservice.repository.PhoneNumbersRepository;
import org.oswfm.employeeservice.model.mapper.PhoneNumbersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhoneNumbersService {

    @Autowired
    private PhoneNumbersRepository repository;

    @Autowired
    private PhoneNumbersMapper mapper;

    /**
     * Create a new PhoneNumbers
     */
    public PhoneNumbersResponseDTO create(PhoneNumbersRequestDTO requestDTO) {
        PhoneNumbers entity = mapper.toEntity(requestDTO);
        PhoneNumbers saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get PhoneNumbers by ID
     */
    @Transactional(readOnly = true)
    public Optional<PhoneNumbersResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all PhoneNumberss
     */
    @Transactional(readOnly = true)
    public List<PhoneNumbersResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing PhoneNumbers
     */
    public Optional<PhoneNumbersResponseDTO> update(Integer id, PhoneNumbersRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    PhoneNumbers updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete PhoneNumbers by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if PhoneNumbers exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all PhoneNumberss
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
