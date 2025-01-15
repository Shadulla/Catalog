package com.shadulla.catalog.modules.categories.application.ports.in;

import com.shadulla.catalog.modules.categories.data.CategoryRequest;
import com.shadulla.catalog.modules.categories.data.CategoryResponse;

public interface CreateCategoryInPort {

    CategoryResponse createCategory(CategoryRequest categoryRequest);
}
