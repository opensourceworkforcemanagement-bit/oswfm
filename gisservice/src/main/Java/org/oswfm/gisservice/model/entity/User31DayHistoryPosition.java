package org.oswfm.gisservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_31_day_history_position")
public class User31DayHistoryPosition extends UserPositionHistory {
    public User31DayHistoryPosition() { super(); }
}
