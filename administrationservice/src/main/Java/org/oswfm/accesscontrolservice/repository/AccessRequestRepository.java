package org.oswfm.accesscontrolservice.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.oswfm.accesscontrolservice.model.entity.AccessRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRequestRepository extends JpaRepository<AccessRequest, Integer > {
    
    List<AccessRequest> findByUser_UserId( Integer  userId);
    
    List<AccessRequest> findByResource_ResourceId( Integer  resourceId);
    
    List<AccessRequest> findByDecision(String decision);
    
    @Query("SELECT ar FROM AccessRequest ar " +
           "WHERE ar.requestTimestamp >= :startDate " +
           "AND ar.requestTimestamp <= :endDate " +
           "ORDER BY ar.requestTimestamp DESC")
    List<AccessRequest> findByDateRange(
            @Param("startDate") OffsetDateTime startDate,
            @Param("endDate") OffsetDateTime endDate
    );
    
    @Query("SELECT ar FROM AccessRequest ar " +
           "WHERE ar.user.userId = :userId " +
           "AND ar.resource.resourceId = :resourceId " +
           "ORDER BY ar.requestTimestamp DESC")
    List<AccessRequest> findByUserAndResource(
            @Param("userId")  Integer  userId,
            @Param("resourceId")  Integer  resourceId
    );
}
