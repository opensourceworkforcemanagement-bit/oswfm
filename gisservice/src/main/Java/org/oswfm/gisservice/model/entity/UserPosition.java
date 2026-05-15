package org.oswfm.gisservice.model.entity;

import java.time.OffsetDateTime;

import org.hibernate.annotations.UpdateTimestamp;
import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_position")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_position_id")
    private Integer userPositionId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "location", nullable = false, columnDefinition = "geography(Point, 4326)")
    private Point location;

    @UpdateTimestamp
    @Column(name = "last_update")
    private OffsetDateTime lastUpdate;
}
