package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesNormalized;
import org.oswfm.timesheetservice.model.entity.TimesheetEntryMinutes;
import org.oswfm.timesheetservice.model.entity.WorkforceCodes;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesNormalizedRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesNormalizedResponseDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntryMinutesRequestDTO;
import org.oswfm.timesheetservice.repository.TimesheetEntriesNormalizedRepository;
import org.oswfm.timesheetservice.repository.TimesheetEntryMinutesNormalizedRepository;
import org.oswfm.timesheetservice.repository.WorkforceCodesRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetEntriesNormalizedMapper;
import org.oswfm.timesheetservice.model.mapper.TimesheetEntryMinutesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TimesheetEntriesNormalizedService {

    @Autowired
    private TimesheetEntriesNormalizedRepository repository;

    @Autowired
    private TimesheetEntryMinutesNormalizedRepository entryMinutesRepository;

    @Autowired
    private WorkforceCodesRepository workforceCodesRepository;

    @Autowired
    private TimesheetEntriesNormalizedMapper mapper;

    @Autowired
    private TimesheetEntryMinutesMapper entryMinutesMapper;

    /**
     * Create a new TimesheetEntriesNormalized with workforce codes and entry minutes.
     */
    public TimesheetEntriesNormalizedResponseDTO create(TimesheetEntriesNormalizedRequestDTO requestDTO) {
        TimesheetEntriesNormalized entity = mapper.toEntity(requestDTO);

        // Link workforce codes
        if (requestDTO.getWorkforceCodeIds() != null && !requestDTO.getWorkforceCodeIds().isEmpty()) {
            Set<WorkforceCodes> workforceCodes = new HashSet<>();
            for (Integer codeId : requestDTO.getWorkforceCodeIds()) {
                workforceCodesRepository.findById(codeId)
                        .ifPresent(workforceCodes::add);
            }
            entity.setWorkforceCodes(workforceCodes);
        }

        // Save the entry first to get the generated ID
        TimesheetEntriesNormalized saved = repository.save(entity);

        // Persist entry minutes
        if (requestDTO.getEntryMinutes() != null && !requestDTO.getEntryMinutes().isEmpty()) {
            List<TimesheetEntryMinutes> minutesList = new ArrayList<>();
            for (TimesheetEntryMinutesRequestDTO minutesDTO : requestDTO.getEntryMinutes()) {
                TimesheetEntryMinutes minutes = entryMinutesMapper.toEntity(minutesDTO);
                minutes.setTimesheetEntriesId(saved.getTimesheetEntriesId());
                minutes.setTimesheetEntry(saved);
                minutesList.add(minutes);
            }
            List<TimesheetEntryMinutes> savedMinutes = entryMinutesRepository.saveAll(minutesList);
            saved.setEntryMinutes(savedMinutes);
            log.debug("Created entry {} with {} minute records", saved.getTimesheetEntriesId(), savedMinutes.size());
        }

        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetEntriesNormalized by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetEntriesNormalizedResponseDTO> getById(Integer id) {
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
    public List<TimesheetEntriesNormalizedResponseDTO> getByTimesheetNormalizedId(Integer timesheetNormalizedId) {
        return repository.findByTimesheetNormalizedId(timesheetNormalizedId).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetEntriesNormalized including workforce codes and entry minutes.
     */
    public Optional<TimesheetEntriesNormalizedResponseDTO> update(Integer id, TimesheetEntriesNormalizedRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);

                    // Update workforce codes if provided
                    if (requestDTO.getWorkforceCodeIds() != null) {
                        Set<WorkforceCodes> workforceCodes = new HashSet<>();
                        for (Integer codeId : requestDTO.getWorkforceCodeIds()) {
                            workforceCodesRepository.findById(codeId)
                                    .ifPresent(workforceCodes::add);
                        }
                        entity.setWorkforceCodes(workforceCodes);
                    }

                    // Replace entry minutes if provided
                    if (requestDTO.getEntryMinutes() != null) {
                        // Remove existing minutes (orphanRemoval on entity handles DB deletion)
                        if (entity.getEntryMinutes() != null) {
                            entity.getEntryMinutes().clear();
                        }

                        List<TimesheetEntryMinutes> newMinutes = new ArrayList<>();
                        for (TimesheetEntryMinutesRequestDTO minutesDTO : requestDTO.getEntryMinutes()) {
                            TimesheetEntryMinutes minutes = entryMinutesMapper.toEntity(minutesDTO);
                            minutes.setTimesheetEntriesId(entity.getTimesheetEntriesId());
                            minutes.setTimesheetEntry(entity);
                            newMinutes.add(minutes);
                        }

                        if (entity.getEntryMinutes() == null) {
                            entity.setEntryMinutes(newMinutes);
                        } else {
                            entity.getEntryMinutes().addAll(newMinutes);
                        }
                        log.debug("Updated entry {} with {} minute records", id, newMinutes.size());
                    }

                    TimesheetEntriesNormalized updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Add workforce code to entry
     */
    public Optional<TimesheetEntriesNormalizedResponseDTO> addWorkforceCode(Integer entryId, Integer workforceCodeId) {
        return repository.findById(entryId)
                .map(entity -> {
                    workforceCodesRepository.findById(workforceCodeId)
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
    public Optional<TimesheetEntriesNormalizedResponseDTO> removeWorkforceCode(Integer entryId, Integer workforceCodeId) {
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
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Delete all entries for a timesheet
     */
    public void deleteByTimesheetNormalizedId(Integer timesheetNormalizedId) {
        repository.deleteByTimesheetNormalizedId(timesheetNormalizedId);
    }

    /**
     * Check if TimesheetEntriesNormalized exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
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
