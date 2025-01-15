package com.shadulla.catalog.modules.categories.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;
import java.util.UUID;


@Data
public class CategoryRequest {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("images")
    private Set<ImageRequest> images;

}