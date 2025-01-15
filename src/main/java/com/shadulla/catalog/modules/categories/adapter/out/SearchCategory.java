package com.shadulla.catalog.modules.categories.adapter.out;

import com.shadulla.catalog.modules.categories.data.CategoryRequest;
import com.shadulla.catalog.modules.categories.data.CategoryResponse;
import com.shadulla.catalog.modules.categories.data.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class SearchCategory {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public SearchCategory(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Page<CategoryResponse> searchCategory(CategoryRequest categoryRequest, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT c.id, c.name, c.description, ci.id AS image_id, ci.image_url, ci.image_description ");
        sql.append("FROM category c ");
        sql.append("LEFT JOIN category_images ci ON c.id = ci.category_id WHERE 1=1 ");

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (categoryRequest.getName() != null && !categoryRequest.getName().isEmpty()) {
            sql.append("AND c.name LIKE :name ");
            params.addValue("name", "%" + categoryRequest.getName() + "%");
        }
        if (categoryRequest.getDescription() != null && !categoryRequest.getDescription().isEmpty()) {
            sql.append("AND c.description LIKE :description ");
            params.addValue("description", "%" + categoryRequest.getDescription() + "%");
        }
        if (categoryRequest.getId() != null) {
            sql.append("AND c.id = :id ");
            params.addValue("id", categoryRequest.getId());
        }

        if (pageable != null) {
            sql.append("LIMIT :limit OFFSET :offset");
            params.addValue("limit", pageable.getPageSize());
            params.addValue("offset", pageable.getOffset());
        }

        String countSql = "SELECT COUNT(*) FROM category c LEFT JOIN category_images ci ON c.id = ci.category_id WHERE 1=1 ";
        StringBuilder countQuery = new StringBuilder(countSql);
        if (categoryRequest.getName() != null && !categoryRequest.getName().isEmpty()) {
            countQuery.append("AND c.name LIKE :name ");
        }
        if (categoryRequest.getDescription() != null && !categoryRequest.getDescription().isEmpty()) {
            countQuery.append("AND c.description LIKE :description ");
        }
        if (categoryRequest.getId() != null) {
            countQuery.append("AND c.id = :id ");
        }

        log.info("Executing query: {}", sql.toString());

        List<CategoryResponse> categoryResponses = namedParameterJdbcTemplate.query(sql.toString(), params, (rs, rowNum) -> {
            CategoryResponse response = new CategoryResponse();
            response.setId(rs.getObject("id", UUID.class));
            response.setName(rs.getString("name"));
            response.setDescription(rs.getString("description"));

            ImageResponse imageResponse = new ImageResponse();
            imageResponse.setId(rs.getObject("image_id", UUID.class));
            imageResponse.setImageUrl(rs.getString("image_url"));
            imageResponse.setImageDescription(rs.getString("image_description"));

            response.setImages(Set.of(imageResponse));
            log.debug("Mapped category response: {}", response);
            return response;
        });

        Integer totalRecords = namedParameterJdbcTemplate.queryForObject(countQuery.toString(), params, Integer.class);
        log.info("Total records found: {}", totalRecords);

        return new PageImpl<>(categoryResponses, pageable, totalRecords != null ? totalRecords : 0);
    }
}
