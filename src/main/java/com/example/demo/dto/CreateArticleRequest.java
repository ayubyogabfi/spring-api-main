package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CreateArticleRequest {

  @NotBlank(message = "Article title must not be empty")
  @Size(min = 3, message = "Article title must be at least 3 characters long")
  @JsonProperty("article_title")
  private String articleTitle;

  // Optional: Either section ID or section Title
  @JsonProperty("section_id")
  private String sectionId;

  @JsonProperty("section_title")
  private String sectionTitle;

  @NotBlank(message = "Body must not be empty")
  @Size(min = 3, message = "Body must be at least 3 characters long")
  private String body;

  @JsonProperty("user_id")
  private String userId;
}