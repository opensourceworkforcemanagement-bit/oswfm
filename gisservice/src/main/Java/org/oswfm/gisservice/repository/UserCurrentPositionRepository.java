package org.oswfm.gisservice.repository;

import java.util.List;

import org.oswfm.gisservice.model.entity.UserCurrentPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCurrentPositionRepository extends JpaRepository<UserCurrentPosition, Integer> {

    @Query(value = """
            SELECT * FROM user_current_position
            WHERE ST_DWithin(location,
                             ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography,
                             :distanceMeters)
            """, nativeQuery = true)
    List<UserCurrentPosition> findNearby(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("distanceMeters") double distanceMeters);
}
