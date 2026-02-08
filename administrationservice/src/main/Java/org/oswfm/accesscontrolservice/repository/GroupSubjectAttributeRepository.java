package org.oswfm.accesscontrolservice.repository;

import java.util.List;

import org.oswfm.accesscontrolservice.model.entity.GroupSubjectAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupSubjectAttributeRepository extends JpaRepository<GroupSubjectAttribute, Integer> {

    List<GroupSubjectAttribute> findByGroup_GroupId(Integer groupId);

    List<GroupSubjectAttribute> findByGroup_GroupIdIn(List<Integer> groupIds);

    void deleteByGroup_GroupIdAndSubjectAttribute_SubjectAttrId(Integer groupId, Integer subjectAttrId);
}
