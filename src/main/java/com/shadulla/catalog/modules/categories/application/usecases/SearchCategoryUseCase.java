package com.shadulla.catalog.modules.categories.application.usecases;

import com.shadulla.catalog.modules.categories.application.ports.in.SearchCategoryInPort;
import com.shadulla.catalog.modules.categories.application.ports.out.SearchCategoryOutPort;
import com.shadulla.catalog.modules.categories.data.CategoryRequest;
import com.shadulla.catalog.modules.categories.data.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public class SearchCategoryUseCase implements SearchCategoryInPort {

    private final SearchCategoryOutPort searchCategoryOutPort;

    public SearchCategoryUseCase(SearchCategoryOutPort searchCategoryOutPort) {
        this.searchCategoryOutPort = searchCategoryOutPort;
    }

    @Override
    public Page<CategoryResponse> searchCategory(CategoryRequest categoryRequest, Pageable pageable) {
        return searchCategoryOutPort.searchCategory(categoryRequest,pageable);
    }
}
