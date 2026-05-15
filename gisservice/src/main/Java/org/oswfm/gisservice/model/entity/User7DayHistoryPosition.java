package org.oswfm.gisservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_7_day_history_position")
public class User7DayHistoryPosition extends UserPositionHistory {
    public User7DayHistoryPosition() { super(); }
}
