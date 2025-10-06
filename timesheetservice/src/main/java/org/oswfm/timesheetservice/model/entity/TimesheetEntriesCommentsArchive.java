package org.oswfm.timesheetservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "timesheet_entries_comments_archive")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntriesCommentsArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "timesheet_entries_comments_id")
    private Integer timesheetEntriesCommentsId;

    @Column(name = "timeshee_id", nullable = false)
    private Integer timesheeId;

    @Column(name = "entry_day", nullable = false)
    private Integer entryDay;

    private String comments;

}
