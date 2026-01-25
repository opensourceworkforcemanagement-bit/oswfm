package org.oswfm.timesheetservice.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.oswfm.timesheetservice.model.timesheet.timesheetstrategynormalized.dto.CreateTimesheetEntryRequest;
import org.oswfm.timesheetservice.model.timesheet.timesheetstrategynormalized.dto.TimesheetEntryDTO;
import org.oswfm.timesheetservice.model.timesheet.timesheetstrategynormalized.dto.UpdateTimesheetEntryRequest;
import org.oswfm.timesheetservice.service.timesheetstrategynormalized.TimesheetEntryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/timesheet-entry")
@RequiredArgsConstructor
@Slf4j
public class TimesheetEntryController {

    private final TimesheetEntryService timesheetEntryService;

    @PostMapping
    public ResponseEntity<TimesheetEntryDTO> createEntry(
            @Valid @RequestBody CreateTimesheetEntryRequest request) {
        log.info("POST /api/v1/timesheet-entries - Creating entry for timesheet: {}", 
                 request.getTimesheetId());
        
        TimesheetEntryDTO created = timesheetEntryService.createEntry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimesheetEntryDTO> getEntryById(@PathVariable Integer id) {
        log.info("GET /api/v1/timesheet-entries/{}", id);
        
        TimesheetEntryDTO entry = timesheetEntryService.getEntryById(id);
        return ResponseEntity.ok(entry);
    }

    @GetMapping
    public ResponseEntity<List<TimesheetEntryDTO>> getEntriesByTimesheetId(
            @RequestParam Integer timesheetId) {
        log.info("GET /api/v1/timesheet-entries?timesheetId={}", timesheetId);
        
        List<TimesheetEntryDTO> entries = timesheetEntryService.getEntriesByTimesheetId(timesheetId);
        return ResponseEntity.ok(entries);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimesheetEntryDTO> updateEntry(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateTimesheetEntryRequest request) {
        log.info("PUT /api/v1/timesheet-entries/{}", id);
        
        TimesheetEntryDTO updated = timesheetEntryService.updateEntry(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Integer id) {
        log.info("DELETE /api/v1/timesheet-entries/{}", id);
        
        timesheetEntryService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-workforce-code")
    public ResponseEntity<List<TimesheetEntryDTO>> getEntriesByWorkforceCode(
            @RequestParam Long workforceCodeId) {
        log.info("GET /api/v1/timesheet-entries/by-workforce-code?workforceCodeId={}",
                 workforceCodeId);

        List<TimesheetEntryDTO> entries = timesheetEntryService.getEntriesByWorkforceCodeId(workforceCodeId);
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<List<TimesheetEntryDTO>> getEntriesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("GET /api/v1/timesheet-entries/by-date-range?startDate={}&endDate={}", 
                 startDate, endDate);
        
        List<TimesheetEntryDTO> entries = timesheetEntryService.getEntriesByDateRange(startDate, endDate);
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/{id}/total-hours")
    public ResponseEntity<BigDecimal> getTotalHoursForEntry(@PathVariable Integer id) {
        log.info("GET /api/v1/timesheet-entries/{}/total-hours", id);
        
        BigDecimal total = timesheetEntryService.getTotalHoursForEntry(id);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/timesheet/{timesheetId}/total-hours")
    public ResponseEntity<BigDecimal> getTotalHoursForTimesheet(@PathVariable Integer timesheetId) {
        log.info("GET /api/v1/timesheet-entries/timesheet/{}/total-hours", timesheetId);
        
        BigDecimal total = timesheetEntryService.getTotalHoursForTimesheet(timesheetId);
        return ResponseEntity.ok(total);
    }
}
