package org.oswfm.gisservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_91_day_history_position")
public class User91DayHistoryPosition extends UserPositionHistory {
    public User91DayHistoryPosition() { super(); }
}
