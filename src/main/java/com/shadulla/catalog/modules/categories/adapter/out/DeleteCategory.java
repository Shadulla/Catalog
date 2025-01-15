package com.shadulla.catalog.modules.categories.adapter.out;

import com.shadulla.catalog.exception.ApplicationException;
import com.shadulla.catalog.modules.categories.adapter.data.Category;
import com.shadulla.catalog.modules.categories.application.ports.out.DeleteCategoryOutPort;
import com.shadulla.catalog.modules.categories.data.CategoryResponse;
import com.shadulla.catalog.utils.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class DeleteCategory implements DeleteCategoryOutPort {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryImageRepository categoryImageRepository;

    @Autowired
    public DeleteCategory(CategoryRepository categoryRepository, CategoryMapper categoryMapper, CategoryImageRepository categoryImageRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.categoryImageRepository = categoryImageRepository;
    }

    @Override
    public CategoryResponse deleteCategory(UUID id) {
        log.info("Attempting to delete category with ID: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Category with ID: {} not found for deletion", id);
                    return new ApplicationException(
                            String.format("Category with ID '%s' not found", id),
                            HttpStatus.NOT_FOUND
                    );
                });

        try {
            deleteCategoryImages(id);
            categoryRepository.delete(category);
            log.info("Category with ID: {} successfully deleted", id);

            return categoryMapper.toDto(category);
        } catch (Exception e) {
            log.error("An error occurred while deleting category with ID: {}", id, e);
            throw new ApplicationException(
                    "An error occurred while deleting the category",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    private void deleteCategoryImages(UUID categoryId) {
        var images = categoryImageRepository.findByCategoryId(categoryId);

        if (images != null && !images.isEmpty()) {
            for (var image : images) {
                try {
                    categoryImageRepository.delete(image);
                    log.info("Successfully deleted image with ID: {}", image.getId());
                } catch (Exception e) {
                    log.error("Error deleting image with ID: {}", image.getId(), e);
                }
            }
        }
    }
}
