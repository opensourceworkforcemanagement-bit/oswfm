package org.oswfm.timesheetservice.service.timesheetstrategynormalized;

import org.oswfm.timesheetservice.model.timesheet.timesheetstrategynormalized.dto.CreateTimesheetEntryRequest;
import org.oswfm.timesheetservice.model.timesheet.timesheetstrategynormalized.dto.TimesheetEntryDTO;
import org.oswfm.timesheetservice.model.timesheet.timesheetstrategynormalized.dto.UpdateTimesheetEntryRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TimesheetEntryService {
    
    TimesheetEntryDTO createEntry(CreateTimesheetEntryRequest request);
    
    TimesheetEntryDTO getEntryById(Integer id);
    
    List<TimesheetEntryDTO> getEntriesByTimesheetId(Integer timesheetId);
    
    TimesheetEntryDTO updateEntry(Integer id, UpdateTimesheetEntryRequest request);
    
    void deleteEntry(Integer id);
    
    List<TimesheetEntryDTO> getEntriesByWorkforceCodeId(Long workforceCodeId);
    
    List<TimesheetEntryDTO> getEntriesByDateRange(LocalDate startDate, LocalDate endDate);
    
    BigDecimal getTotalHoursForEntry(Integer entryId);
    
    BigDecimal getTotalHoursForTimesheet(Integer timesheetId);
}
