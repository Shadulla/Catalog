package com.shadulla.catalog.utils;

import com.shadulla.catalog.modules.categories.adapter.data.Category;
import com.shadulla.catalog.modules.categories.adapter.data.CategoryImage;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class CategorySpecification {

    public static Specification<Category> getCategories(String name, String description, UUID id) {
        return (Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            Join<Category, CategoryImage> categoryImageJoin = null;
            if (query != null) {
                categoryImageJoin = root.join("categoryImages", JoinType.LEFT);
            }

            if (name != null && !name.isEmpty()) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }

            if (description != null && !description.isEmpty()) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(root.get("description"), "%" + description + "%"));
            }

            if (id != null) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("id"), id));
            }

            if (categoryImageJoin != null) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(categoryImageJoin.get("categoryId"), root.get("id")));
            }

            return predicates;
        };
    }
}
