package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.Addresses;
import org.oswfm.employeeservice.model.dto.AddressesRequestDTO;
import org.oswfm.employeeservice.model.dto.AddressesResponseDTO;
import org.oswfm.employeeservice.repository.AddressesRepository;
import org.oswfm.employeeservice.model.mapper.AddressesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AddressesService {

    @Autowired
    private AddressesRepository repository;

    @Autowired
    private AddressesMapper mapper;

    /**
     * Create a new Addresses
     */
    public AddressesResponseDTO create(AddressesRequestDTO requestDTO) {
        Addresses entity = mapper.toEntity(requestDTO);
        Addresses saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get Addresses by ID
     */
    @Transactional(readOnly = true)
    public Optional<AddressesResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all Addressess
     */
    @Transactional(readOnly = true)
    public List<AddressesResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing Addresses
     */
    public Optional<AddressesResponseDTO> update(Integer id, AddressesRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    Addresses updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete Addresses by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if Addresses exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all Addressess
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
