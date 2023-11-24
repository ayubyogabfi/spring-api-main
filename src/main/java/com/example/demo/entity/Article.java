package com.example.demo.entity;

import com.example.demo.entity.BaseEntity;
import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tx_article")
public class Article extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String sectionTitle;

  private String articleTitle;

  private String body;
}