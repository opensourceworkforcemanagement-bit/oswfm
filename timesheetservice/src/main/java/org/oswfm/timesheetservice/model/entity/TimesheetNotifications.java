package org.oswfm.timesheetservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;

@Entity
@Table(name = "timesheet_notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetNotifications {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "timesheet_notification_id")
    private Integer timesheetNotificationId;

    @Column(name = "timesheet_id", nullable = false)
    private Integer timesheetId;

    @Column(name = "recipient_id", nullable = false)
    private Integer recipientId;

    @Column(name = "notification_type", nullable = false)
    private String notificationType;

    @Column(name = "sent_at")
    private LocalTime sentAt;

    private Integer status;

}
