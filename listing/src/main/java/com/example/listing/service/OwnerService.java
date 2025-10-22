package com.example.listing.service;

import com.example.listing.dto.OwnerByIdResponse;
import com.example.listing.exception.OwnerException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OwnerService {
    private final WebClient webClient;

    public OwnerService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8082/api/v1/owners").build();
    }

    public OwnerByIdResponse getOwnerById(Long ownerId) {
        try {
            return webClient.get()
                    .uri("/{id}", ownerId)
                    .exchangeToMono(response -> {
                        if (response.statusCode().is2xxSuccessful()) {
                            return response.bodyToMono(OwnerByIdResponse.class);
                        } else if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                            return Mono.error(new OwnerException("Owner not found"));
                        } else {
                            return Mono.error(new RuntimeException(
                                    "Owner service error: " + response.statusCode()));
                        }
                    })
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch owner: " + e.getMessage(), e);
        }
    }
}
