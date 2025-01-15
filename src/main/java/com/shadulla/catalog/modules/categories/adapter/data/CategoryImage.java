package com.shadulla.catalog.modules.categories.adapter.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "category_images")
@Data
public class CategoryImage extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "image_description")
    private String imageDescription;

}
