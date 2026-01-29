package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetEntryMinutes;
import org.oswfm.timesheetservice.model.dto.TimesheetEntryMinutesRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntryMinutesResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetEntryMinutesNormalizedRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetEntryMinutesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetEntryMinutesNormalizedService {

    @Autowired
    private TimesheetEntryMinutesNormalizedRepository repository;

    @Autowired
    private TimesheetEntryMinutesMapper mapper;

    /**
     * Create a new TimesheetEntryMinutes
     */
    public TimesheetEntryMinutesResponseDTO create(TimesheetEntryMinutesRequestDTO requestDTO) {
        TimesheetEntryMinutes entity = mapper.toEntity(requestDTO);
        TimesheetEntryMinutes saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Create multiple TimesheetEntryMinutes
     */
    public List<TimesheetEntryMinutesResponseDTO> createBatch(List<TimesheetEntryMinutesRequestDTO> requestDTOs) {
        List<TimesheetEntryMinutes> entities = requestDTOs.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());
        List<TimesheetEntryMinutes> saved = repository.saveAll(entities);
        return saved.stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get TimesheetEntryMinutes by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetEntryMinutesResponseDTO> getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetEntryMinutes
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntryMinutesResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get TimesheetEntryMinutes by timesheet entries ID
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntryMinutesResponseDTO> getByTimesheetEntriesId(Long timesheetEntriesId) {
        return repository.findByTimesheetEntriesId(timesheetEntriesId).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get TimesheetEntryMinutes by timesheet entries ID and day of week
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntryMinutesResponseDTO> getByTimesheetEntriesIdAndDayOfWeek(Long timesheetEntriesId, String dayOfWeek) {
        return repository.findByTimesheetEntriesIdAndDayOfWeek(timesheetEntriesId, dayOfWeek).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get TimesheetEntryMinutes by timesheet entries ID and date
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntryMinutesResponseDTO> getByTimesheetEntriesIdAndDate(Long timesheetEntriesId, Date date) {
        return repository.findByTimesheetEntriesIdAndDate(timesheetEntriesId, date).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetEntryMinutes
     */
    public Optional<TimesheetEntryMinutesResponseDTO> update(Long id, TimesheetEntryMinutesRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetEntryMinutes updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetEntryMinutes by ID
     */
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Delete all minutes for a timesheet entry
     */
    public void deleteByTimesheetEntriesId(Long timesheetEntriesId) {
        repository.deleteByTimesheetEntriesId(timesheetEntriesId);
    }

    /**
     * Check if TimesheetEntryMinutes exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetEntryMinutes
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
