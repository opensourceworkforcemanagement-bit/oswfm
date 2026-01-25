package org.oswfm.timesheetservice.model.entity.timesheetstrategynormalized;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "timesheet_entries_normalized")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timesheet_entries_id")
    private Integer timesheetEntryId;

    @Column(name = "timesheet_id", nullable = false)
    private Integer timesheetId;

    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "timesheet_entries_id")
    @ToString.Exclude
    private List<TimesheetEntryCode> codes = new ArrayList<>();

    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "timesheet_entry_id")
    @ToString.Exclude
    private List<TimesheetEntryMinutes> minutes = new ArrayList<>();

    // Helper methods for bidirectional relationship management
    public void addCode(TimesheetEntryCode code) {
        codes.add(code);
    }

    public void removeCode(TimesheetEntryCode code) {
        codes.remove(code);
    }

    public void addMinutes(TimesheetEntryMinutes minutesEntry) {
        minutes.add(minutesEntry);
    }

    public void removeMinutes(TimesheetEntryMinutes minutesEntry) {
        minutes.remove(minutesEntry);
    }

    public void clearCodes() {
        codes.clear();
    }

    public void clearMinutes() {
        minutes.clear();
    }
}
