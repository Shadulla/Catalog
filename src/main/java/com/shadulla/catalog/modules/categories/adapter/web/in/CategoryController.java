package com.shadulla.catalog.modules.categories.adapter.web.in;

import com.shadulla.catalog.exception.ApplicationException;
import com.shadulla.catalog.modules.categories.adapter.out.CreateCategory;
import com.shadulla.catalog.modules.categories.adapter.out.DeleteCategory;
import com.shadulla.catalog.modules.categories.adapter.out.SearchCategory;
import com.shadulla.catalog.modules.categories.adapter.out.UpdateCategory;
import com.shadulla.catalog.modules.categories.data.CategoryRequest;
import com.shadulla.catalog.modules.categories.data.CategoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@Slf4j
public class CategoryController {

    @Autowired
    private CreateCategory createCategory;

    @Autowired
    private UpdateCategory updateCategory;

    @Autowired
    private SearchCategory searchCategory;

    @Autowired
    private DeleteCategory deleteCategory;

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> searchCategories(
            @RequestParam(value = "id", defaultValue = "") UUID id,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "description", defaultValue = "") String description,
           Pageable pageable) {

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setId(id);
        categoryRequest.setDescription(description);
        categoryRequest.setName(name);
        Page<CategoryResponse> categoryResponses = searchCategory.searchCategory(categoryRequest, pageable);

        if (categoryResponses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(categoryResponses, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        log.info("Creating new category with name: {}", categoryRequest.getName());

        CategoryResponse savedCategory = createCategory.createCategory(categoryRequest);
        log.info("Category created with id: {}", savedCategory.getId());

        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable UUID id, @RequestBody CategoryRequest categoryRequest) {
        log.info("Updating category with id: {}", id);

        CategoryResponse updatedCategory = updateCategory.updateCategory(id,categoryRequest);
        if (updatedCategory != null) {
            log.info("Category updated with id: {}", id);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } else {
            log.warn("Category not found with id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        log.info("Delete category with id: {}", id);
            deleteCategory.deleteCategory(id);
            log.info("Category deleted with id: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
