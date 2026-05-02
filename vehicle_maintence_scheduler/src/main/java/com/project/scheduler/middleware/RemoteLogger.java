package com.project.scheduler.middleware;

import com.project.scheduler.dto.LogData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

@Component
public class RemoteLogger {

    private final String ENDPOINT = "https://20.207.122.201/evaluation-service/logs";
    
    @Value("${log.api.token:}")
    private String apiToken;

    private final RestTemplate client;

    public RemoteLogger() {
        this.client = buildInsecureClient();
    }

    public void dispatchLog(String stack, String level, String pkgName, String msg) {
        List<String> validLevels = Arrays.asList("debug", "info", "warn", "error", "fatal");
        if (!validLevels.contains(level)) return;

        LogData payload = new LogData(stack, level, pkgName, msg);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (apiToken != null && !apiToken.isBlank()) {
            headers.set("Authorization", "Bearer " + apiToken.trim());
        }

        try {
            client.postForEntity(ENDPOINT, new HttpEntity<>(payload, headers), String.class);
            System.out.println("Dispatched log: " + msg);
        } catch (Exception ex) {
            System.err.println("Log dispatch failed: " + ex.getMessage());
        }
    }

    private RestTemplate buildInsecureClient() {
        try {
            TrustManager[] trustAll = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] c, String authType) { }
                    public void checkServerTrusted(X509Certificate[] c, String authType) { }
                }
            };
            SSLContext sslCtx = SSLContext.getInstance("TLS");
            sslCtx.init(null, trustAll, new java.security.SecureRandom());

            org.springframework.http.client.SimpleClientHttpRequestFactory factory = new org.springframework.http.client.SimpleClientHttpRequestFactory() {
                @Override
                protected void prepareConnection(java.net.HttpURLConnection conn, String method) throws java.io.IOException {
                    if (conn instanceof HttpsURLConnection) {
                        ((HttpsURLConnection) conn).setHostnameVerifier((h, s) -> true);
                        ((HttpsURLConnection) conn).setSSLSocketFactory(sslCtx.getSocketFactory());
                    }
                    super.prepareConnection(conn, method);
                }
            };
            return new RestTemplate(factory);
        } catch (Exception e) {
            return new RestTemplate();
        }
    }
}
