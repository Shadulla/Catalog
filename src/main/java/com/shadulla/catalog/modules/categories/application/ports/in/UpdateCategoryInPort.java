package com.shadulla.catalog.modules.categories.application.ports.in;

import com.shadulla.catalog.modules.categories.data.CategoryRequest;
import com.shadulla.catalog.modules.categories.data.CategoryResponse;

import java.util.UUID;

public interface UpdateCategoryInPort {

    CategoryResponse updateategory(UUID id, CategoryRequest categoryRequest);
}
