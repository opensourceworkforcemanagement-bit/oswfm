package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.dto.TimesheetEntryCreateDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntryWeekDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetNormalizedRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetNormalizedResponseDTO;
import org.oswfm.timesheetservice.model.dto.WorkforceCodeRefDTO;
import org.oswfm.timesheetservice.model.entity.TimesheetEntriesNormalized;
import org.oswfm.timesheetservice.model.entity.TimesheetEntryMinutes;
import org.oswfm.timesheetservice.model.entity.TimesheetNormalized;
import org.oswfm.timesheetservice.model.entity.WorkforceCodes;
import org.oswfm.timesheetservice.model.mapper.TimesheetNormalizedMapper;
import org.oswfm.timesheetservice.repository.TimesheetEntriesNormalizedRepository;
import org.oswfm.timesheetservice.repository.TimesheetNormalizedRepository;
import org.oswfm.timesheetservice.repository.WorkforceCodesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TimesheetNormalizedService {

    @Autowired
    private TimesheetNormalizedRepository repository;

    @Autowired
    private TimesheetEntriesNormalizedRepository entriesRepository;

    @Autowired
    private WorkforceCodesRepository workforceCodesRepository;

    @Autowired
    private TimesheetNormalizedMapper mapper;

    // Day-of-week keys matching the frontend Weeks interface
    private static final String[] DAY_KEYS = {"U", "M", "T", "W", "R", "F", "S"};

    /**
     * Create a new TimesheetNormalized with optional nested entries.
     * Accepts the full structure from the frontend:
     * { employeeId, payPeriodId, timesheetEntries: [{ workforceCode, accountCode, weeks }] }
     */
    public TimesheetNormalizedResponseDTO create(TimesheetNormalizedRequestDTO requestDTO) {
        // 1. Save the timesheet header
        TimesheetNormalized entity = mapper.toEntity(requestDTO);
        if (entity.getTimesheetTypeId() == null) {
            entity.setTimesheetTypeId(1); // default type
        }
        if (entity.getStatus() == null) {
            entity.setStatus(0); // default draft status
        }
        TimesheetNormalized saved = repository.save(entity);
        log.debug("Created timesheet normalized with id: {}", saved.getTimesheetNormalizedId());

        // 2. Process nested entries if present
        List<TimesheetEntryCreateDTO> entryDTOs = requestDTO.getTimesheetEntries();
        if (entryDTOs != null && !entryDTOs.isEmpty()) {
            // Collect all referenced workforce code IDs for batch lookup
            Set<Integer> allCodeIds = new HashSet<>();
            for (TimesheetEntryCreateDTO entryDTO : entryDTOs) {
                if (entryDTO.getWorkforceCode() != null && entryDTO.getWorkforceCode().getId() != null) {
                    allCodeIds.add(entryDTO.getWorkforceCode().getId());
                }
                if (entryDTO.getAccountCode() != null && entryDTO.getAccountCode().getId() != null) {
                    allCodeIds.add(entryDTO.getAccountCode().getId());
                }
            }

            // Batch-load workforce codes
            Map<Integer, WorkforceCodes> codesById = workforceCodesRepository.findAllById(allCodeIds)
                    .stream()
                    .collect(Collectors.toMap(WorkforceCodes::getId, Function.identity()));

            for (TimesheetEntryCreateDTO entryDTO : entryDTOs) {
                // Create the entry
                TimesheetEntriesNormalized entry = new TimesheetEntriesNormalized();
                entry.setTimesheetNormalizedId(saved.getTimesheetNormalizedId());

                // Link workforce codes (work code + account code)
                Set<WorkforceCodes> codes = new HashSet<>();
                addCodeIfPresent(codes, codesById, entryDTO.getWorkforceCode());
                addCodeIfPresent(codes, codesById, entryDTO.getAccountCode());
                entry.setWorkforceCodes(codes);

                // Convert weeks to entry minutes
                List<TimesheetEntryMinutes> minutesList = new ArrayList<>();
                if (entryDTO.getWeeks() != null) {
                    for (TimesheetEntryWeekDTO week : entryDTO.getWeeks()) {
                        addMinutesForWeek(minutesList, week, entry);
                    }
                }
                entry.setEntryMinutes(minutesList);

                entriesRepository.save(entry);
                log.debug("Created entry with {} codes and {} minute records",
                        codes.size(), minutesList.size());
            }
        }

        // Return with entries populated
        return mapper.toResponseDTO(repository.findById(saved.getTimesheetNormalizedId()).orElse(saved));
    }

    private void addCodeIfPresent(Set<WorkforceCodes> codes, Map<Integer, WorkforceCodes> codesById, WorkforceCodeRefDTO ref) {
        if (ref != null && ref.getId() != null) {
            WorkforceCodes code = codesById.get(ref.getId());
            if (code != null) {
                codes.add(code);
            } else {
                log.warn("Workforce code with id {} not found, skipping", ref.getId());
            }
        }
    }

    private void addMinutesForWeek(List<TimesheetEntryMinutes> minutesList, TimesheetEntryWeekDTO week,
                                   TimesheetEntriesNormalized entry) {
        BigDecimal[] hours = {
            week.getSunHours(), week.getMonHours(), week.getTueHours(),
            week.getWedHours(), week.getThuHours(), week.getFriHours(),
            week.getSatHours()
        };

        for (int i = 0; i < DAY_KEYS.length; i++) {
            BigDecimal value = hours[i];
            if (value != null && value.compareTo(BigDecimal.ZERO) != 0) {
                TimesheetEntryMinutes minutes = new TimesheetEntryMinutes();
                minutes.setTimesheetEntry(entry);
                minutes.setMinutes(value.multiply(BigDecimal.valueOf(60))); // convert hours to minutes
                minutes.setDayOfWeek(DAY_KEYS[i]);
                minutesList.add(minutes);
            }
        }
    }

    /**
     * Get TimesheetNormalized by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetNormalizedResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get TimesheetNormalized by ID without entries
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetNormalizedResponseDTO> getByIdWithoutEntries(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTOWithoutEntries);
    }

    /**
     * Get all TimesheetNormalized
     */
    @Transactional(readOnly = true)
    public List<TimesheetNormalizedResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTOWithoutEntries)
                .collect(Collectors.toList());
    }

    /**
     * Get TimesheetNormalized by employee ID
     */
    @Transactional(readOnly = true)
    public List<TimesheetNormalizedResponseDTO> getByEmployeeId(Integer employeeId) {
        return repository.findByEmployeeId(employeeId).stream()
                .map(mapper::toResponseDTOWithoutEntries)
                .collect(Collectors.toList());
    }

    /**
     * Get TimesheetNormalized by pay period ID
     */
    @Transactional(readOnly = true)
    public List<TimesheetNormalizedResponseDTO> getByPayPeriodId(Integer payPeriodId) {
        return repository.findByPayPeriodId(payPeriodId).stream()
                .map(mapper::toResponseDTOWithoutEntries)
                .collect(Collectors.toList());
    }

    /**
     * Get TimesheetNormalized by status
     */
    @Transactional(readOnly = true)
    public List<TimesheetNormalizedResponseDTO> getByStatus(Integer status) {
        return repository.findByStatus(status).stream()
                .map(mapper::toResponseDTOWithoutEntries)
                .collect(Collectors.toList());
    }

    /**
     * Get TimesheetNormalized by employee ID and pay period ID
     */
    @Transactional(readOnly = true)
    public List<TimesheetNormalizedResponseDTO> getByEmployeeIdAndPayPeriodId(Integer employeeId, Integer payPeriodId) {
        return repository.findByEmployeeIdAndPayPeriodId(employeeId, payPeriodId).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get TimesheetNormalized by employee ID and status
     */
    @Transactional(readOnly = true)
    public List<TimesheetNormalizedResponseDTO> getByEmployeeIdAndStatus(Integer employeeId, Integer status) {
        return repository.findByEmployeeIdAndStatus(employeeId, status).stream()
                .map(mapper::toResponseDTOWithoutEntries)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetNormalized
     */
    public Optional<TimesheetNormalizedResponseDTO> update(Integer id, TimesheetNormalizedRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetNormalized updated = repository.save(entity);
                    return mapper.toResponseDTOWithoutEntries(updated);
                });
    }

    /**
     * Update status only
     */
    public Optional<TimesheetNormalizedResponseDTO> updateStatus(Integer id, Integer status) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setStatus(status);
                    TimesheetNormalized updated = repository.save(entity);
                    return mapper.toResponseDTOWithoutEntries(updated);
                });
    }

    /**
     * Delete TimesheetNormalized by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetNormalized exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetNormalized
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
