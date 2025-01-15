package com.shadulla.catalog.modules.categories.application.ports.out;

import com.shadulla.catalog.modules.categories.data.CategoryRequest;
import com.shadulla.catalog.modules.categories.data.CategoryResponse;

import java.util.UUID;

public interface UpdateCategoryOutPort {
    CategoryResponse updateCategory(UUID id, CategoryRequest categoryRequest);
}
