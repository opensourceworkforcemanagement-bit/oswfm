package org.oswfm.accesscontrolservice.repository;

import java.util.List;
import java.util.Optional;

import org.oswfm.commons.model.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ACUserRepository extends JpaRepository<UserEntity, Integer > {
    
    Optional<UserEntity> findByUserName(String username);    
  
    List<UserEntity> findByUserStatus(Integer userStatus);
    
    boolean existsByUserName(String username);
    
}
