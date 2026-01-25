package org.oswfm.timesheetservice.model.entity.timesheetstrategynormalized;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "timesheet_entry_minutes",
    uniqueConstraints = @UniqueConstraint(
        name = "unique_entry_date",
        columnNames = {"timesheet_entry_id", "date"}
    )
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntryMinutes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "timesheet_entry_id", nullable = false)
    private Long timesheetEntryId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "minutes", nullable = false, precision = 5, scale = 2)
    private BigDecimal minutes;

    @Column(name = "day_of_week",  nullable = false)
    private String dayOfWeek;

    public TimesheetEntryMinutes(LocalDate date, BigDecimal minutes) {
        this.date = date;
        this.minutes = minutes;
    }
}
