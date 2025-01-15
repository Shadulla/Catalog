package com.shadulla.catalog.modules.categories.adapter.data;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "category")
@Data
public class Category extends  AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String description;
    @Transient
    private Set<CategoryImage> images = new HashSet<>();
}
