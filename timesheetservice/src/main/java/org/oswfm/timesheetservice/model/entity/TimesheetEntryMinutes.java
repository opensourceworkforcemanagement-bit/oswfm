package org.oswfm.timesheetservice.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "timesheet_entry_minutes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntryMinutes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "timesheet_entries_id", nullable = false)
    private Long timesheetEntriesId;

    @Column(name = "minutes", nullable = false, precision = 5, scale = 2)
    private BigDecimal minutes;

    @Column(name = "day_of_week", length = 1)
    private String dayOfWeek;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timesheet_entries_id", referencedColumnName = "timesheet_entries_id", insertable = false, updatable = false)
    private TimesheetEntriesNormalized timesheetEntry;
}
