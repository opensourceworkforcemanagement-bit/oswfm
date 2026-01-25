package org.oswfm.timesheetservice.model.entity.timesheetstrategynormalized;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Join table entity representing the many-to-many relationship
 * between timesheet entries and workforce codes
 */
@Entity
@Table(name = "timesheet_entry_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TimesheetEntryCodeId.class)
public class TimesheetEntryCode {

    @Id
    @Column(name = "timesheet_entries_id", nullable = false)
    private Long timesheetEntry;

    @Id
    @Column(name = "workforce_codes_id", nullable = false)
    private Long workforceCode;
}
