package com.shadulla.catalog.modules.categories.data;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
public class ImageResponse{

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("image_description")
    private String imageDescription;

}