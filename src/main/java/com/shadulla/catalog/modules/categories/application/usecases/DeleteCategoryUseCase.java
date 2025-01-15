package com.shadulla.catalog.modules.categories.application.usecases;

import com.shadulla.catalog.modules.categories.application.ports.in.DeleteCategoryInPort;
import com.shadulla.catalog.modules.categories.application.ports.out.DeleteCategoryOutPort;
import com.shadulla.catalog.modules.categories.data.CategoryResponse;

import java.util.UUID;


public class DeleteCategoryUseCase implements DeleteCategoryInPort {

    private final DeleteCategoryOutPort deleteCategoryOutPort;


    public DeleteCategoryUseCase(DeleteCategoryOutPort deleteCategoryOutPort) {
        this.deleteCategoryOutPort = deleteCategoryOutPort;
    }

    @Override
    public CategoryResponse deleteCategory(UUID id) {
        return deleteCategoryOutPort.deleteCategory(id);
    }
}
