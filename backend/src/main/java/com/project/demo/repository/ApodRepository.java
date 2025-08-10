package com.project.demo.repository;

import com.project.demo.model.Apod;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ApodRepository extends ElasticsearchRepository<Apod, String> {
    // Find all APOD entries by title containing the given keyword, case-insensitive
    List<Apod> findByTitleContainingIgnoreCase(String keyword);

    // Find all APOD entries by explanation containing the given keyword, case-insensitive
    List<Apod> findByExplanationContainingIgnoreCase(String keyword);

    // Find all APOD entries by authors containing the given keyword, case-insensitive
//    List<Apod> findByAuthorsContainingIgnoreCase(String keyword);
}
