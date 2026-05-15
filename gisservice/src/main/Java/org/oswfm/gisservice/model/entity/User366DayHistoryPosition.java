package org.oswfm.gisservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_366_day_history_position")
public class User366DayHistoryPosition extends UserPositionHistory {
    public User366DayHistoryPosition() { super(); }
}
