package org.oswfm.timesheetservice.model.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntryCodesId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "timesheet_entries_id")
    private Integer timesheetEntriesId;

    @Column(name = "workforce_codes_id")
    private Integer workforceCodesId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimesheetEntryCodesId that = (TimesheetEntryCodesId) o;
        return Objects.equals(timesheetEntriesId, that.timesheetEntriesId) &&
               Objects.equals(workforceCodesId, that.workforceCodesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timesheetEntriesId, workforceCodesId);
    }
}
