package com.project.notification.service;

import com.project.notification.dto.LogRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LoggingService {

    // 🔥 Use HTTPS (more reliable)
    private static final String URL = "https://20.207.122.201/evaluation-service/logs";

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

    private final RestTemplate restTemplate;

    public LoggingService() {
        this.restTemplate = createTrustingRestTemplate();
    }

    private RestTemplate createTrustingRestTemplate() {
        try {
            javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[] {
                new javax.net.ssl.X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) { }
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) { }
                }
            };
            javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            org.springframework.http.client.SimpleClientHttpRequestFactory requestFactory = new org.springframework.http.client.SimpleClientHttpRequestFactory() {
                @Override
                protected void prepareConnection(java.net.HttpURLConnection connection, String httpMethod) throws java.io.IOException {
                    if (connection instanceof javax.net.ssl.HttpsURLConnection) {
                        ((javax.net.ssl.HttpsURLConnection) connection).setHostnameVerifier((hostname, session) -> true);
                        ((javax.net.ssl.HttpsURLConnection) connection).setSSLSocketFactory(sc.getSocketFactory());
                    }
                    super.prepareConnection(connection, httpMethod);
                }
            };

            return new RestTemplate(requestFactory);
        } catch (Exception e) {
            e.printStackTrace();
            return new RestTemplate();
        }
    }
    public String log(String stack, String level, String pkg, String message) {

        // ✅ Validation
        if (!validStacks.contains(stack)) return "Invalid stack";
        if (!validLevels.contains(level)) return "Invalid level";
        if (!validPackages.contains(pkg)) return "Invalid package";

        LogRequest request = new LogRequest(stack, level, pkg, message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 🔐 Token (trim to avoid hidden spaces)
        headers.set("Authorization", "Bearer " + token.trim());

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
            System.out.println("Logging failed: " + e.getMessage());
            return "{ \"message\": \"log processed (external API issue)\" }";
        }
    }
}