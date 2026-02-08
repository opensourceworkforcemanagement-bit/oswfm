package org.oswfm.accesscontrolservice.repository;

import org.oswfm.accesscontrolservice.model.entity.SubjectAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface SubjectAttributeRepository extends JpaRepository<SubjectAttribute, Integer> {

    List<SubjectAttribute> findByAttribute_AttributeId(Integer attributeId);

    @Query("SELECT sa FROM SubjectAttribute sa " +
           "WHERE sa.subjectAttrId IN :ids " +
           "AND sa.validFrom <= :currentTime " +
           "AND (sa.validUntil IS NULL OR sa.validUntil > :currentTime)")
    List<SubjectAttribute> findActiveByIds(
            @Param("ids") List<Integer> ids,
            @Param("currentTime") OffsetDateTime currentTime
    );
}
