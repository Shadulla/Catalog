package com.shadulla.catalog.modules.categories.adapter.out;

import com.shadulla.catalog.modules.categories.adapter.data.Category;
import com.shadulla.catalog.modules.categories.adapter.data.CategoryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Set;
import java.util.UUID;

public interface CategoryImageRepository  extends JpaRepository<CategoryImage, UUID>, JpaSpecificationExecutor<CategoryImage> {
    Set<CategoryImage> findByCategoryId(UUID categoryId);
}
