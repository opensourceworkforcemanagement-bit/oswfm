package org.oswfm.gisservice.repository;

import java.util.List;
import java.util.Optional;

import org.oswfm.gisservice.model.entity.UserPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPositionRepository extends JpaRepository<UserPosition, Integer> {

    Optional<UserPosition> findByUserId(Integer userId);

    List<UserPosition> findAllByUserId(Integer userId);

    boolean existsByUserId(Integer userId);

    void deleteByUserId(Integer userId);

    @Query(value = "SELECT * FROM user_position WHERE ST_DWithin(location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography, :distanceMeters)", nativeQuery = true)
    List<UserPosition> findUsersWithinDistance(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("distanceMeters") double distanceMeters);

    @Query(value = "SELECT DISTINCT ON (user_id) * FROM user_position ORDER BY user_id, last_update DESC", nativeQuery = true)
    List<UserPosition> findLatestPositionPerUser();
}
