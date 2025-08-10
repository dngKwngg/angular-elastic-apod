package com.project.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "apod")
public class Apod {
    @Id
    private String date;
    private String title;
    private String explanation;
    @JsonProperty("image_url")
    private String imageUrl;
    private String authors;
}
