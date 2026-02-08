package org.oswfm.accesscontrolservice.repository;

import java.util.List;

import org.oswfm.accesscontrolservice.model.entity.UserSubjectAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSubjectAttributeRepository extends JpaRepository<UserSubjectAttribute, Integer> {

    List<UserSubjectAttribute> findByUser_UserId(Integer userId);

    void deleteByUser_UserIdAndSubjectAttribute_SubjectAttrId(Integer userId, Integer subjectAttrId);
}
