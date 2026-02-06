package org.oswfm.accesscontrolservice.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.oswfm.accesscontrolservice.model.entity.ResourceAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceAttributeRepository extends JpaRepository<ResourceAttribute, Integer > {
    
    List<ResourceAttribute> findByResource_ResourceId( Integer resourceId);
    
    List<ResourceAttribute> findByAttribute_AttributeId( Integer attributeId);
    
    @Query("SELECT ra FROM ResourceAttribute ra " +
           "WHERE ra.resource.resourceId = :resourceId " +
           "AND ra.validFrom <= :currentTime " +
           "AND (ra.validUntil IS NULL OR ra.validUntil > :currentTime)")
    List<ResourceAttribute> findActiveAttributesByResourceId(
            @Param("resourceId")  Integer resourceId,
            @Param("currentTime") OffsetDateTime currentTime
    );
    
    @Query("SELECT ra FROM ResourceAttribute ra " +
           "WHERE ra.resource.resourceId = :resourceId " +
           "AND ra.attribute.attributeId = :attributeId " +
           "AND ra.validFrom <= :currentTime " +
           "AND (ra.validUntil IS NULL OR ra.validUntil > :currentTime)")
    List<ResourceAttribute> findActiveAttributesByResourceIdAndAttributeId(
            @Param("resourceId")  Integer resourceId,
            @Param("attributeId")  Integer attributeId,
            @Param("currentTime") OffsetDateTime currentTime
    );
}
