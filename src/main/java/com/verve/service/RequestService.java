package com.verve.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.verve.model.Request;
import com.verve.repository.RedisRepository;

@Service
public class RequestService {

    private static final Logger logger = Logger.getLogger(RequestService.class.getName());

    private ConcurrentHashMap<Integer, Boolean> uniqueRequests = new ConcurrentHashMap<>();
    private AtomicInteger uniqueRequestCount = new AtomicInteger(0);

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private LoggingService loggingService;

    @Autowired
    private RedisRepository redisRepository;

    public String processRequest(Request request) {
        boolean isUnique = redisRepository.isUniqueRequest(request.getId());

        if (isUnique) {
            redisRepository.addRequest(request.getId());
            int count = uniqueRequestCount.incrementAndGet();
            logger.info("Unique Request Count: " + count);
            loggingService.logUniqueRequestCount(count);
            
            if (request.getEndpoint() != null) {
                sendHttpRequest(request.getEndpoint(), count, false);
            }

            return "ok";
        } else {
            return "failed";
        }
    }
    
    public String acceptPostRequest(Request request) {
        boolean isUnique = redisRepository.isUniqueRequest(request.getId());

        if (isUnique) {
            redisRepository.addRequest(request.getId());
            int count = uniqueRequestCount.incrementAndGet();
            logger.info("Unique Request Count: " + count);
            loggingService.logUniqueRequestCount(count);

            if (request.getEndpoint() != null) {
                sendHttpRequest(request.getEndpoint(), count, true);
            }

            return "ok";
        } else {
            return "failed";
        }
    }

    private void sendHttpRequest(String endpoint, int count, boolean isPost) {
        try {
            WebClient webClient = webClientBuilder.baseUrl(endpoint).build();

            if (isPost) {
                webClient.post()
                        .uri(uriBuilder -> uriBuilder.build())
                        .bodyValue("{\"count\": " + count + "}")
                        .retrieve()
                        .toBodilessEntity()
                        .doOnTerminate(() -> logger.info("POST request sent to " + endpoint))
                        .doOnError(WebClientResponseException.class, error ->
                                logger.warning("POST request failed: " + error.getMessage()))
                        .subscribe();
            } else {
                webClient.get()
                        .uri(uriBuilder -> uriBuilder.queryParam("count", count).build())
                        .retrieve()
                        .toBodilessEntity()
                        .doOnTerminate(() -> logger.info("GET request sent to " + endpoint))
                        .doOnError(WebClientResponseException.class, error ->
                                logger.warning("GET request failed: " + error.getMessage()))
                        .subscribe();
            }
        } catch (Exception e) {
            logger.warning("Error sending request to endpoint: " + e.getMessage());
        }
    }
}
