package com.shadulla.catalog.modules.categories.application.ports.out;

import com.shadulla.catalog.modules.categories.data.CategoryRequest;
import com.shadulla.catalog.modules.categories.data.CategoryResponse;

public interface CreateCategoryOutPort {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
}
