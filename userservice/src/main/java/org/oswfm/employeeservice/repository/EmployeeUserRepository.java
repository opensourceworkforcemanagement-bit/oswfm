package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.EmployeeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeUserRepository extends JpaRepository<EmployeeUser, Integer> {

    List<EmployeeUser> findByEmployeeId(Integer employeeId);

    List<EmployeeUser> findByUserId(Integer userId);
}
