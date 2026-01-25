package org.oswfm.timesheetservice.service.timesheetstrategynormalized.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.oswfm.timesheetservice.exception.ResourceNotFoundException;
import org.oswfm.timesheetservice.model.entity.timesheetstrategynormalized.TimesheetEntry;
import org.oswfm.timesheetservice.model.entity.timesheetstrategynormalized.TimesheetEntryCode;
import org.oswfm.timesheetservice.model.entity.timesheetstrategynormalized.TimesheetEntryMinutes;
import org.oswfm.timesheetservice.model.timesheet.timesheetstrategynormalized.dto.CodeDTO;
import org.oswfm.timesheetservice.model.timesheet.timesheetstrategynormalized.dto.CreateTimesheetEntryRequest;
import org.oswfm.timesheetservice.model.timesheet.timesheetstrategynormalized.dto.TimesheetEntryDTO;
import org.oswfm.timesheetservice.model.timesheet.timesheetstrategynormalized.dto.UpdateTimesheetEntryRequest;
import org.oswfm.timesheetservice.repository.TimesheetEntryMinutesRepository;
import org.oswfm.timesheetservice.repository.TimesheetEntryRepository;
import org.oswfm.timesheetservice.service.timesheetstrategynormalized.TimesheetEntryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TimesheetEntryServiceImpl implements TimesheetEntryService {

    private final TimesheetEntryRepository timesheetEntryRepository;
    private final TimesheetEntryMinutesRepository timesheetEntryMinutesRepository;

    @Override
    public TimesheetEntryDTO createEntry(CreateTimesheetEntryRequest request) {
        log.info("Creating timesheet entry for timesheet ID: {}", request.getTimesheetId());

        TimesheetEntry entry = new TimesheetEntry();
        entry.setTimesheetId(request.getTimesheetId());

        // Save entry first to get the ID
        TimesheetEntry savedEntry = timesheetEntryRepository.save(entry);
        final Long entryId = savedEntry.getTimesheetEntryId().longValue();

        // Add codes with the saved entry ID
        request.getCodes().forEach((codeType, codeRequest) -> {
            TimesheetEntryCode code = new TimesheetEntryCode();
            code.setTimesheetEntry(entryId);
            code.setWorkforceCode(codeRequest.getWorkforceCodeId());

            entry.addCode(code);
        });

        // Add minutes
        request.getMinutes().forEach((date, minutes) -> {
            TimesheetEntryMinutes minuteEntry = new TimesheetEntryMinutes(date, minutes);
            entry.addMinutes(minuteEntry);
        });

        // Save again with codes and minutes
        TimesheetEntry finalEntry = timesheetEntryRepository.save(entry);
        log.info("Created timesheet entry with ID: {}", finalEntry.getTimesheetEntryId());

        return convertToDTO(finalEntry);
    }

    @Override
    @Transactional(readOnly = true)
    public TimesheetEntryDTO getEntryById(Integer id) {
        log.debug("Fetching timesheet entry by ID: {}", id);
        
        TimesheetEntry entry = timesheetEntryRepository.findByIdWithCodesAndHours(id)
            .orElseThrow(() -> new ResourceNotFoundException("Timesheet entry not found with ID: " + id));
        
        return convertToDTO(entry);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimesheetEntryDTO> getEntriesByTimesheetId(Integer timesheetId) {
        log.debug("Fetching timesheet entries for timesheet ID: {}", timesheetId);
        
        List<TimesheetEntry> entries = timesheetEntryRepository.findByTimesheetIdWithCodesAndHours(timesheetId);
        
        return entries.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public TimesheetEntryDTO updateEntry(Integer id, UpdateTimesheetEntryRequest request) {
        log.info("Updating timesheet entry ID: {}", id);

        TimesheetEntry entry = timesheetEntryRepository.findByIdWithCodesAndHours(id)
            .orElseThrow(() -> new ResourceNotFoundException("Timesheet entry not found with ID: " + id));

        final Long entryId = entry.getTimesheetEntryId().longValue();

        // Update codes if provided
        if (request.getCodes() != null) {
            entry.clearCodes();
            request.getCodes().forEach((codeType, codeRequest) -> {
                TimesheetEntryCode code = new TimesheetEntryCode();
                code.setTimesheetEntry(entryId);
                code.setWorkforceCode(codeRequest.getWorkforceCodeId());

                entry.addCode(code);
            });
        }

        // Update Minutes if provided
        if (request.getMinutes() != null) {
            entry.clearMinutes();
            request.getMinutes().forEach((date, minutes) -> {
                TimesheetEntryMinutes hourEntry = new TimesheetEntryMinutes(date, minutes);
                entry.addMinutes(hourEntry);
            });
        }

        TimesheetEntry updatedEntry = timesheetEntryRepository.save(entry);
        log.info("Updated timesheet entry ID: {}", id);

        return convertToDTO(updatedEntry);
    }

    @Override
    public void deleteEntry(Integer id) {
        log.info("Deleting timesheet entry ID: {}", id);
        
        if (!timesheetEntryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Timesheet entry not found with ID: " + id);
        }
        
        timesheetEntryRepository.deleteById(id);
        log.info("Deleted timesheet entry ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimesheetEntryDTO> getEntriesByWorkforceCodeId(Long workforceCodeId) {
        log.debug("Fetching entries by workforce code ID: {}", workforceCodeId);

        List<TimesheetEntry> entries = timesheetEntryRepository.findByWorkforceCodeId(workforceCodeId);

        return entries.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimesheetEntryDTO> getEntriesByDateRange(LocalDate startDate, LocalDate endDate) {
        log.debug("Fetching entries by date range: {} to {}", startDate, endDate);
        
        List<TimesheetEntry> entries = timesheetEntryRepository.findByDateRange(startDate, endDate);
        
        return entries.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalHoursForEntry(Integer entryId) {
        log.debug("Calculating total hours for entry ID: {}", entryId);

        BigDecimal total = timesheetEntryMinutesRepository.sumMinutesByEntry(entryId.longValue());
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalHoursForTimesheet(Integer timesheetId) {
        log.debug("Calculating total hours for timesheet ID: {}", timesheetId);

        BigDecimal total = timesheetEntryMinutesRepository.sumMinutesByTimesheet(timesheetId);
        return total != null ? total : BigDecimal.ZERO;
    }

    // Helper method to convert entity to DTO
    private TimesheetEntryDTO convertToDTO(TimesheetEntry entry) {
        TimesheetEntryDTO dto = new TimesheetEntryDTO();
        dto.setTimesheetEntryId(entry.getTimesheetEntryId());
        dto.setTimesheetId(entry.getTimesheetId());

        // Convert codes to map (workforceCodeId as String key -> CodeDTO)
        Map<String, CodeDTO> codesMap = entry.getCodes().stream()
            .collect(Collectors.toMap(
                code -> code.getWorkforceCode().toString(),
                code -> new CodeDTO(
                    code.getTimesheetEntry(),
                    code.getWorkforceCode()
                )
            ));
        dto.setCodes(codesMap);

        // Convert minutes to map
        Map<LocalDate, BigDecimal> minutesMap = entry.getMinutes().stream()
            .collect(Collectors.toMap(
                TimesheetEntryMinutes::getDate,
                TimesheetEntryMinutes::getMinutes
            ));
        dto.setMinutes(minutesMap);

        return dto;
    }
}
