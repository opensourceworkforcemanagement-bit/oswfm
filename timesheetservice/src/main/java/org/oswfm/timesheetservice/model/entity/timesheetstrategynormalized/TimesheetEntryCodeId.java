package org.oswfm.timesheetservice.model.entity.timesheetstrategynormalized;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Composite primary key for TimesheetEntryCode
 * Represents the many-to-many relationship between timesheet entries and workforce codes
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntryCodeId implements Serializable {

    private Long timesheetEntry;
    private Long workforceCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimesheetEntryCodeId that = (TimesheetEntryCodeId) o;
        return Objects.equals(timesheetEntry, that.timesheetEntry) &&
               Objects.equals(workforceCode, that.workforceCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timesheetEntry, workforceCode);
    }
}
