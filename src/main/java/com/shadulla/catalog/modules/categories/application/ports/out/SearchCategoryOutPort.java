package com.shadulla.catalog.modules.categories.application.ports.out;

import com.shadulla.catalog.modules.categories.data.CategoryRequest;
import com.shadulla.catalog.modules.categories.data.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchCategoryOutPort {

    public Page<CategoryResponse> searchCategory(CategoryRequest categoryRequest, Pageable pageable);
}
