package com.shadulla.catalog.modules.categories.application.usecases;

import com.shadulla.catalog.modules.categories.application.ports.in.CreateCategoryInPort;
import com.shadulla.catalog.modules.categories.application.ports.out.CreateCategoryOutPort;
import com.shadulla.catalog.modules.categories.data.CategoryRequest;
import com.shadulla.catalog.modules.categories.data.CategoryResponse;


public class CreateCategoryUseCase implements CreateCategoryInPort {

    private final CreateCategoryOutPort createCategoryOutPort;


    public CreateCategoryUseCase(CreateCategoryOutPort createCategoryOutPort) {
        this.createCategoryOutPort = createCategoryOutPort;
    }


    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        return createCategoryOutPort.createCategory(categoryRequest);
    }
}
