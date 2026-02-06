package org.oswfm.accesscontrolservice.repository;

import java.util.Optional;

import org.oswfm.accesscontrolservice.model.entity.AttributeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeCategoryRepository extends JpaRepository<AttributeCategory, Integer> {

    Optional<AttributeCategory> findByCategoryName(String categoryName);

    boolean existsByCategoryName(String categoryName);
}
