package com.shadulla.catalog.modules.categories.adapter.out;

import com.shadulla.catalog.exception.ApplicationException;
import com.shadulla.catalog.modules.categories.adapter.data.Category;
import com.shadulla.catalog.modules.categories.adapter.data.CategoryImage;
import com.shadulla.catalog.modules.categories.application.ports.out.UpdateCategoryOutPort;
import com.shadulla.catalog.modules.categories.data.CategoryRequest;
import com.shadulla.catalog.modules.categories.data.CategoryResponse;
import com.shadulla.catalog.modules.categories.data.ImageRequest;
import com.shadulla.catalog.utils.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class UpdateCategory implements UpdateCategoryOutPort {

    private final CategoryRepository categoryRepository;
    private final CategoryImageRepository categoryImageRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public UpdateCategory(CategoryRepository categoryRepository,
                          CategoryImageRepository categoryImageRepository,
                          CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryImageRepository = categoryImageRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponse updateCategory(UUID id, CategoryRequest categoryRequest) {
        log.info("Attempting to update category with ID: {}", id);

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Category with ID: {} not found for update", id);
                    return new ApplicationException(
                            String.format("Category with ID '%s' not found", id),
                            HttpStatus.NOT_FOUND
                    );
                });

        existingCategory.setName(categoryRequest.getName());
        existingCategory.setDescription(categoryRequest.getDescription());

        Set<CategoryImage> updatedImages = new HashSet<>();
        for (ImageRequest imageRequest : categoryRequest.getImages()) {
            Optional<CategoryImage> existingImageOpt = existingCategory.getImages().stream()
                    .filter(image -> image.getId() != null && image.getId().equals(imageRequest.getId()))
                    .findFirst();

            CategoryImage image;
            if (existingImageOpt.isPresent()) {
                image = existingImageOpt.get();
                image.setImageUrl(imageRequest.getImageUrl());
                image.setImageDescription(imageRequest.getImageDescription());
            } else {
                // Add new image
                image = new CategoryImage();
                image.setImageUrl(imageRequest.getImageUrl());
                image.setImageDescription(imageRequest.getImageDescription());
                image.setCategoryId(existingCategory.getId());
            }
            updatedImages.add(image);
        }

        existingCategory.getImages().removeIf(image -> updatedImages.stream()
                .noneMatch(updatedImage -> updatedImage.getId() != null && updatedImage.getId().equals(image.getId())));

        existingCategory.setImages(updatedImages);

        try {
            Category updatedCategory = categoryRepository.save(existingCategory);

            log.info("Category with ID: {} successfully updated", updatedCategory.getId());
            return categoryMapper.toDto(updatedCategory);
        } catch (Exception e) {
            log.error("An error occurred while updating category with ID: {}", id, e);
            throw new ApplicationException(
                    "An error occurred while updating the category",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
