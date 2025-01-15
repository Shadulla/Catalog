package com.shadulla.catalog.modules.categories.adapter.out;

import com.shadulla.catalog.modules.categories.adapter.data.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>, JpaSpecificationExecutor<Category> {

    Page<Category> findByNameContainingAndDescriptionContaining(String name, String description, Pageable pageable);
    boolean existsByName(String name);
}
