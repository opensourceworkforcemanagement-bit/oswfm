package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesNormalized;
import org.oswfm.timesheetservice.model.entity.WorkforceCodes;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesNormalizedRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesNormalizedResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetEntriesNormalizedRepository;
import org.oswfm.timesheetservice.repository.WorkforceCodesRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetEntriesNormalizedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetEntriesNormalizedService {

    @Autowired
    private TimesheetEntriesNormalizedRepository repository;

    @Autowired
    private WorkforceCodesRepository workforceCodesRepository;

    @Autowired
    private TimesheetEntriesNormalizedMapper mapper;

    /**
     * Create a new TimesheetEntriesNormalized
     */
    public TimesheetEntriesNormalizedResponseDTO create(TimesheetEntriesNormalizedRequestDTO requestDTO) {
        TimesheetEntriesNormalized entity = mapper.toEntity(requestDTO);

        if (requestDTO.getWorkforceCodeIds() != null && !requestDTO.getWorkforceCodeIds().isEmpty()) {
            Set<WorkforceCodes> workforceCodes = new HashSet<>();
            for (Long codeId : requestDTO.getWorkforceCodeIds()) {
                workforceCodesRepository.findById(codeId.intValue())
                        .ifPresent(workforceCodes::add);
            }
            entity.setWorkforceCodes(workforceCodes);
        }

        TimesheetEntriesNormalized saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetEntriesNormalized by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetEntriesNormalizedResponseDTO> getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetEntriesNormalized
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntriesNormalizedResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get TimesheetEntriesNormalized by timesheet normalized ID
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntriesNormalizedResponseDTO> getByTimesheetNormalizedId(Long timesheetNormalizedId) {
        return repository.findByTimesheetNormalizedId(timesheetNormalizedId).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetEntriesNormalized
     */
    public Optional<TimesheetEntriesNormalizedResponseDTO> update(Long id, TimesheetEntriesNormalizedRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);

                    if (requestDTO.getWorkforceCodeIds() != null) {
                        Set<WorkforceCodes> workforceCodes = new HashSet<>();
                        for (Long codeId : requestDTO.getWorkforceCodeIds()) {
                            workforceCodesRepository.findById(codeId.intValue())
                                    .ifPresent(workforceCodes::add);
                        }
                        entity.setWorkforceCodes(workforceCodes);
                    }

                    TimesheetEntriesNormalized updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Add workforce code to entry
     */
    public Optional<TimesheetEntriesNormalizedResponseDTO> addWorkforceCode(Long entryId, Long workforceCodeId) {
        return repository.findById(entryId)
                .map(entity -> {
                    workforceCodesRepository.findById(workforceCodeId.intValue())
                            .ifPresent(code -> {
                                if (entity.getWorkforceCodes() == null) {
                                    entity.setWorkforceCodes(new HashSet<>());
                                }
                                entity.getWorkforceCodes().add(code);
                            });
                    TimesheetEntriesNormalized updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Remove workforce code from entry
     */
    public Optional<TimesheetEntriesNormalizedResponseDTO> removeWorkforceCode(Long entryId, Long workforceCodeId) {
        return repository.findById(entryId)
                .map(entity -> {
                    if (entity.getWorkforceCodes() != null) {
                        entity.getWorkforceCodes().removeIf(code -> code.getId().equals(workforceCodeId));
                    }
                    TimesheetEntriesNormalized updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetEntriesNormalized by ID
     */
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Delete all entries for a timesheet
     */
    public void deleteByTimesheetNormalizedId(Long timesheetNormalizedId) {
        repository.deleteByTimesheetNormalizedId(timesheetNormalizedId);
    }

    /**
     * Check if TimesheetEntriesNormalized exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetEntriesNormalized
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
