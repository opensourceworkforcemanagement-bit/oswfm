package org.oswfm.timesheetservice.model.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "timesheet_entry_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntryCodes {

    @EmbeddedId
    private TimesheetEntryCodesId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("timesheetEntriesId")
    @JoinColumn(name = "timesheet_entries_id")
    private TimesheetEntriesNormalized timesheetEntry;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("workforceCodesId")
    @JoinColumn(name = "workforce_codes_id")
    private WorkforceCodes workforceCode;

    public TimesheetEntryCodes(TimesheetEntriesNormalized timesheetEntry, WorkforceCodes workforceCode) {
        this.timesheetEntry = timesheetEntry;
        this.workforceCode = workforceCode;
        this.id = new TimesheetEntryCodesId(timesheetEntry.getTimesheetEntriesId(), workforceCode.getId());
    }
}
