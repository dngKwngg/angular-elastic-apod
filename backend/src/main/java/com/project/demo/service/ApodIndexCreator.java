package com.project.demo.service;

import com.project.demo.model.Apod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Component;

@Component
public class ApodIndexCreator implements CommandLineRunner {

    private final ElasticsearchOperations elasticsearchOperations;
    private static final Logger logger = LoggerFactory.getLogger(ApodIndexCreator.class);

    public ApodIndexCreator(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public void run(String... args) throws Exception {
        IndexOperations indexOperations = elasticsearchOperations.indexOps(Apod.class);
        if (!indexOperations.exists()) {
            indexOperations.create();
            indexOperations.putMapping(indexOperations.createMapping(Apod.class));
            indexOperations.refresh();
            logger.info("APOD index created successfully.");
        } else {
            logger.info("APOD index already exists, skipping creation.");
        }
    }
}
