package com.project.logging.service;

import com.project.logging.dto.LogRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LoggingService {

    private static final String URL = "http://20.207.122.201/evaluation-service/logs";

    @Value("${log.api.token}")
    private String token;

    private final List<String> validStacks = List.of("backend", "frontend");
    private final List<String> validLevels = List.of("debug", "info", "warn", "error", "fatal");
    private final List<String> validPackages = List.of(
            "cache","controller","cron_job","db","domain",
            "handler","repository","route","service",
            "api","component","hook","page","state","style",
            "auth","config","middleware","utils"
    );

    private final RestTemplate restTemplate = new RestTemplate();

    public String log(String stack, String level, String pkg, String message) {

        // ✅ Validation
        if (!validStacks.contains(stack)) return "Invalid stack";
        if (!validLevels.contains(level)) return "Invalid level";
        if (!validPackages.contains(pkg)) return "Invalid package";

        LogRequest request = new LogRequest(stack, level, pkg, message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 🔐 Add Bearer Token
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<LogRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    URL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            return response.getBody();

        } catch (Exception e) {
            e.printStackTrace();  // 🔥 shows actual error in console
            return "{ \"message\": \"log processed (external API issue)\" }";
        }
    }
}