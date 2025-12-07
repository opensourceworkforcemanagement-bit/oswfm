package org.oswfm.accesscontrolservice.repository;

import org.oswfm.accesscontrolservice.model.entity.SubjectAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface SubjectAttributeRepository extends JpaRepository<SubjectAttribute, Integer > {
    
    List<SubjectAttribute> findByUser_UserId(Integer userId);
    
    List<SubjectAttribute> findByAttribute_AttributeId(Integer attributeId);
    
    @Query("SELECT sa FROM SubjectAttribute sa " +
           "WHERE sa.user.userId = :userId " +
           "AND sa.validFrom <= :currentTime " +
           "AND (sa.validUntil IS NULL OR sa.validUntil > :currentTime)")
    List<SubjectAttribute> findActiveAttributesByUserId(
            @Param("userId") Integer  userId,
            @Param("currentTime") OffsetDateTime currentTime
    );
    
    @Query("SELECT sa FROM SubjectAttribute sa " +
           "WHERE sa.user.userId = :userId " +
           "AND sa.attribute.attributeId = :attributeId " +
           "AND sa.validFrom <= :currentTime " +
           "AND (sa.validUntil IS NULL OR sa.validUntil > :currentTime)")
    List<SubjectAttribute> findActiveAttributesByUserIdAndAttributeId(
            @Param("userId")  Integer userId,
            @Param("attributeId")  Integer attributeId,
            @Param("currentTime") OffsetDateTime currentTime
    );
}
