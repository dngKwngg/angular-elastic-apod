package com.project.demo.controller;

import com.project.demo.model.Apod;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.multiMatch;


@RestController
@RequestMapping("/api/apod")
public class ApodController {
    private final ElasticsearchOperations elasticsearchOperations;

    public ApodController(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    /**
     * Search APOD entries by title or explanation.
     *
     * @param query the search query
     * @param from the starting index for pagination
     * @param size the number of results to return
     * @return a list of APOD entries matching the search criteria
     */
    @GetMapping("/search")
    public List<Apod> search(@RequestParam String query,
                             @RequestParam(defaultValue = "0") int from,
                             @RequestParam(defaultValue = "10") int size) {
        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(multiMatch(m -> m
                        .query(query)
                        .fields("title", "explanation")))
                .withPageable(PageRequest.of(from / size, size))
                .build();

        SearchHits<Apod> searchHits = elasticsearchOperations.search(searchQuery, Apod.class);
        // Count the total number of hits
        long totalHits = searchHits.getTotalHits();
        System.out.println("Total hits: " + totalHits);
        return searchHits.stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());
    }

}