package org.oswfm.gisservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_180_day_history_position")
public class User180DayHistoryPosition extends UserPositionHistory {
    public User180DayHistoryPosition() { super(); }
}
