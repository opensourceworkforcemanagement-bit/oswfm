package org.oswfm.timesheetservice.model.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "timesheet_entries_normalized")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntriesNormalized {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timesheet_entries_id")
    private Long timesheetEntriesId;

    @Column(name = "timesheet_normalized_id", nullable = false)
    private Long timesheetNormalizedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timesheet_normalized_id", referencedColumnName = "timesheet_normalized_id", insertable = false, updatable = false)
    private TimesheetNormalized timesheetNormalized;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "timesheet_entry_codes",
        joinColumns = @JoinColumn(name = "timesheet_entries_id"),
        inverseJoinColumns = @JoinColumn(name = "workforce_codes_id")
    )
    private Set<WorkforceCodes> workforceCodes;

    @OneToMany(mappedBy = "timesheetEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimesheetEntryMinutes> entryMinutes;
}
