package com.example.demo.repository;

import com.example.demo.entity.Section;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
  List<Section> findAllByOrderByTitleAsc();

  @Query(
    value = "SELECT ts from Section INNER JOIN ArticleSection tas \n" +
    "ON ts.id = tas.section_id WHERE :sectionId = ts.id" +
    "AND tas.deleted_date = null " +
    "AND ts.created_by = :userId " // will be developed further
  )
  Optional<Section> findSectionIdOnArticleSection(String sectionId, String userId);

  @Query(
    value = "SELECT ts from Section INNER JOIN ArticleSection tas \n" +
    "ON ts.id = tas.section_id WHERE :sectionTitle = ts.title" +
    "AND tas.deleted_date = null " +
    "AND ts.created_by = ':userId" // will be developed further
  )
  Optional<Section> findSectionTitleOnArticleSection(String sectionTitle, String userId);

  @Modifying
  @Query(
    value = "INSERT INTO Section (id, title, body, created_by, created_from, " +
    "deleted_date) values (nextval('tm_section_id_seq'::regclass), :sectionTitle, " +
    "null, :userId, 'localhost', null)" //will developed further
  )
  void createSectionBySectionTitle(String sectionTitle, String userId);
}
