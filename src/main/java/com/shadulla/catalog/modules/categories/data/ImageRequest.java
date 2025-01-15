package com.shadulla.catalog.modules.categories.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class ImageRequest {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("image_description")
    private String imageDescription;
}
