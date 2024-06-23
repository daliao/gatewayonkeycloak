package com.lxg.keycloak.caller.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/{subname:private|res1|res2}")
public class CallerController {

    private WebClient webClient;

    public CallerController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/ping")
    public Mono<String> ping(HttpServletRequest request) {
        String callmePath = request.getRequestURI();
        String callmeInfo = webClient
                .get()
                .uri("http://localhost:8040" + callmePath)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> Mono.error(new RuntimeException("Client error")))
                .onStatus(status -> status.is5xxServerError(), response -> Mono.error(new RuntimeException("Server error")))
                .bodyToMono(String.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    // Handle WebClientResponseException here
                    return Mono.error(new RuntimeException("Error response from server: " + ex.getStatusCode()));
                })
                .onErrorResume(Exception.class, ex -> {
                    // Handle generic exception here
                    return Mono.error(new RuntimeException("General error: " + ex.getMessage(), ex));
                })
                .block();
        return Mono.just("Callme: " + callmeInfo);
    }
}
