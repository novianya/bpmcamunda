package com.asnovikova.bpmcamunda.component;

import com.asnovikova.bpmcamunda.model.CatFact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Anna Novikova
 */
@Service
public class CatFactService {

    private final RestTemplate restTemplate;

    private ObjectMapper objectMapper;

    private static final String CAT_FACT_URL = "https://catfact.ninja/fact";

    Logger logger  = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    public CatFactService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public CatFact getFact() throws JsonProcessingException {

        String rawResponse = restTemplate.getForObject(CAT_FACT_URL, String.class);
        logger.debug("response from {} is {}", CAT_FACT_URL, rawResponse);
        return objectMapper.readValue(rawResponse, CatFact.class);
    }
}
