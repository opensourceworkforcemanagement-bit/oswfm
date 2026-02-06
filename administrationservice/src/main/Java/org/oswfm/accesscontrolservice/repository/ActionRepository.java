package org.oswfm.accesscontrolservice.repository;

import java.util.Optional;

import org.oswfm.accesscontrolservice.model.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action, Integer > {
    
    Optional<Action> findByActionName(String actionName);
    
    boolean existsByActionName(String actionName);
}
