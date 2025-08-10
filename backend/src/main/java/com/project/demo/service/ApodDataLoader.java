package com.project.demo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.demo.model.Apod;
import com.project.demo.repository.ApodRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class ApodDataLoader {
    private static final Logger logger = LoggerFactory.getLogger(ApodDataLoader.class);

    private final ApodRepository apodRepository;

    public ApodDataLoader(ApodRepository apodRepository) {
        this.apodRepository = apodRepository;
    }

    @PostConstruct
    public void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = new ClassPathResource("apod.json").getInputStream();

            List<Apod> entries = mapper.readValue(is, new TypeReference<List<Apod>>() {});
            apodRepository.saveAll(entries);

            logger.info("✅ Loaded {} entries into Elasticsearch index 'apod'.", entries.size());
        } catch (IOException e) {
            logger.error("❌ Failed to load APOD data: {}", e.getMessage(), e);
        }
    }
}
