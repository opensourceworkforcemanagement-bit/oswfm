package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.AccountCodes;
import org.oswfm.timesheetservice.model.dto.AccountCodesRequestDTO;
import org.oswfm.timesheetservice.model.dto.AccountCodesResponseDTO;
import org.oswfm.timesheetservice.repository.AccountCodesRepository;
import org.oswfm.timesheetservice.model.mapper.AccountCodesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountCodesService {

    @Autowired
    private AccountCodesRepository repository;

    @Autowired
    private AccountCodesMapper mapper;

    /**
     * Create a new AccountCodes
     */
    public AccountCodesResponseDTO create(AccountCodesRequestDTO requestDTO) {
        AccountCodes entity = mapper.toEntity(requestDTO);
        AccountCodes saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get AccountCodes by ID
     */
    @Transactional(readOnly = true)
    public Optional<AccountCodesResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all AccountCodess
     */
    @Transactional(readOnly = true)
    public List<AccountCodesResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing AccountCodes
     */
    public Optional<AccountCodesResponseDTO> update(Integer id, AccountCodesRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    AccountCodes updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete AccountCodes by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if AccountCodes exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all AccountCodess
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
