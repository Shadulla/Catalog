package com.shadulla.catalog.modules.categories.application.ports.in;

import com.shadulla.catalog.modules.categories.data.CategoryResponse;

import java.util.UUID;

public interface DeleteCategoryInPort {

    CategoryResponse deleteCategory(UUID id);
}
