package com.shadulla.catalog.modules.categories.adapter.out;

import com.shadulla.catalog.exception.ApplicationException;
import com.shadulla.catalog.modules.categories.adapter.data.Category;
import com.shadulla.catalog.modules.categories.adapter.data.CategoryImage;
import com.shadulla.catalog.modules.categories.application.ports.out.CreateCategoryOutPort;
import com.shadulla.catalog.modules.categories.data.CategoryRequest;
import com.shadulla.catalog.modules.categories.data.CategoryResponse;
import com.shadulla.catalog.modules.categories.data.ImageRequest;
import com.shadulla.catalog.utils.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@Transactional
public class CreateCategory implements CreateCategoryOutPort {

    private final CategoryRepository categoryRepository;
    private final CategoryImageRepository categoryImageRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CreateCategory(CategoryRepository categoryRepository,
                          CategoryImageRepository categoryImageRepository,
                          CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryImageRepository = categoryImageRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.getName())) {
            throw new ApplicationException(
                    String.format("Category with name '%s' already exists", categoryRequest.getName()),
                    HttpStatus.CONFLICT
            );
        }

        Category category = categoryMapper.toEntity(categoryRequest);

        try {
            Category savedCategory = categoryRepository.save(category);

            Set<CategoryImage> images = new HashSet<>();
            for (ImageRequest imageRequest : categoryRequest.getImages()) {
                CategoryImage categoryImage = new CategoryImage();
                categoryImage.setImageUrl(imageRequest.getImageUrl());
                categoryImage.setImageDescription(imageRequest.getImageDescription());
                categoryImage.setCategoryId(savedCategory.getId());
                images.add(categoryImage);
            }

            if (!images.isEmpty()) {
                categoryImageRepository.saveAll(images);
                log.info("Successfully saved {} images for category ID: {}", images.size(), savedCategory.getId());
            }

            savedCategory.setImages(images);

            return categoryMapper.toDto(savedCategory);
        } catch (Exception ex) {
            log.error("Error occurred while saving the category or images: {}", ex.getMessage(), ex);
            throw new ApplicationException("Failed to save category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
