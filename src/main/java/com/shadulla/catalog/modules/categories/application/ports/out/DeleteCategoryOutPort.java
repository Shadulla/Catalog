package com.shadulla.catalog.modules.categories.application.ports.out;

import com.shadulla.catalog.modules.categories.data.CategoryResponse;

import java.util.UUID;

public interface DeleteCategoryOutPort {
    CategoryResponse deleteCategory(UUID id);
}
