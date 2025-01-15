package com.shadulla.catalog.modules.categories.application.usecases;

import com.shadulla.catalog.modules.categories.application.ports.in.UpdateCategoryInPort;
import com.shadulla.catalog.modules.categories.application.ports.out.UpdateCategoryOutPort;
import com.shadulla.catalog.modules.categories.data.CategoryRequest;
import com.shadulla.catalog.modules.categories.data.CategoryResponse;

import java.util.UUID;


public class UpdateCategoryUseCase implements UpdateCategoryInPort {

    private final UpdateCategoryOutPort updateCategoryOutPort;


    public UpdateCategoryUseCase(UpdateCategoryOutPort updateCategoryOutPort) {
        this.updateCategoryOutPort = updateCategoryOutPort;
    }

    @Override
    public CategoryResponse updateategory(UUID id, CategoryRequest categoryRequest) {
        return updateCategoryOutPort.updateCategory(id,categoryRequest);
    }
}
