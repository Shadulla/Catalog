package com.shadulla.catalog.utils;


import com.shadulla.catalog.modules.categories.adapter.data.Category;
import com.shadulla.catalog.modules.categories.data.CategoryRequest;
import com.shadulla.catalog.modules.categories.data.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryResponse toDto(Category category);

    Category toEntity(CategoryRequest categoryRequest);
}
