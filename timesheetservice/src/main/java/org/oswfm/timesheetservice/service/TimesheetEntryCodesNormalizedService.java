package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetEntryCodes;
import org.oswfm.timesheetservice.model.entity.TimesheetEntryCodesId;
import org.oswfm.timesheetservice.model.entity.TimesheetEntriesNormalized;
import org.oswfm.timesheetservice.model.entity.WorkforceCodes;
import org.oswfm.timesheetservice.model.dto.TimesheetEntryCodesRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntryCodesResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetEntryCodesNormalizedRepository;
import org.oswfm.timesheetservice.repository.TimesheetEntriesNormalizedRepository;
import org.oswfm.timesheetservice.repository.WorkforceCodesRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetEntryCodesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetEntryCodesNormalizedService {

    @Autowired
    private TimesheetEntryCodesNormalizedRepository repository;

    @Autowired
    private TimesheetEntriesNormalizedRepository entriesRepository;

    @Autowired
    private WorkforceCodesRepository workforceCodesRepository;

    @Autowired
    private TimesheetEntryCodesMapper mapper;

    /**
     * Create a new association between timesheet entry and workforce code
     */
    public Optional<TimesheetEntryCodesResponseDTO> create(TimesheetEntryCodesRequestDTO requestDTO) {
        Optional<TimesheetEntriesNormalized> entry = entriesRepository.findById(requestDTO.getTimesheetEntriesId());
        Optional<WorkforceCodes> workforceCode = workforceCodesRepository.findById(requestDTO.getWorkforceCodesId());

        if (entry.isPresent() && workforceCode.isPresent()) {
            TimesheetEntryCodes entity = new TimesheetEntryCodes(entry.get(), workforceCode.get());
            TimesheetEntryCodes saved = repository.save(entity);
            return Optional.of(mapper.toResponseDTO(saved));
        }

        return Optional.empty();
    }

    /**
     * Get TimesheetEntryCodes by composite ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetEntryCodesResponseDTO> getById(Integer timesheetEntriesId, Integer workforceCodesId) {
        TimesheetEntryCodesId id = new TimesheetEntryCodesId(timesheetEntriesId, workforceCodesId);
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetEntryCodes
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntryCodesResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get TimesheetEntryCodes by timesheet entries ID
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntryCodesResponseDTO> getByTimesheetEntriesId(Integer timesheetEntriesId) {
        return repository.findByIdTimesheetEntriesId(timesheetEntriesId).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get TimesheetEntryCodes by workforce codes ID
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntryCodesResponseDTO> getByWorkforceCodesId(Integer workforceCodesId) {
        return repository.findByIdWorkforceCodesId(workforceCodesId).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Delete TimesheetEntryCodes by composite ID
     */
    public boolean delete(Integer timesheetEntriesId, Integer workforceCodesId) {
        TimesheetEntryCodesId id = new TimesheetEntryCodesId(timesheetEntriesId, workforceCodesId);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Delete all codes for a timesheet entry
     */
    public void deleteByTimesheetEntriesId(Integer timesheetEntriesId) {
        repository.deleteByIdTimesheetEntriesId(timesheetEntriesId);
    }

    /**
     * Delete all entries for a workforce code
     */
    public void deleteByWorkforceCodesId(Integer workforceCodesId) {
        repository.deleteByIdWorkforceCodesId(workforceCodesId);
    }

    /**
     * Check if association exists
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer timesheetEntriesId, Integer workforceCodesId) {
        return repository.existsByIdTimesheetEntriesIdAndIdWorkforceCodesId(timesheetEntriesId, workforceCodesId);
    }

    /**
     * Get count of all TimesheetEntryCodes
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
