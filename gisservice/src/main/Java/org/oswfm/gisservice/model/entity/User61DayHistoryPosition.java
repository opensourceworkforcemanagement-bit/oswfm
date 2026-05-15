package org.oswfm.gisservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_61_day_history_position")
public class User61DayHistoryPosition extends UserPositionHistory {
    public User61DayHistoryPosition() { super(); }
}
