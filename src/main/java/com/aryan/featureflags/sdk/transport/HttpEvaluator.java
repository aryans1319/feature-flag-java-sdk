package com.aryan.featureflags.sdk.transport;

import com.aryan.featureflags.sdk.config.SdkConfig;
import com.aryan.featureflags.sdk.context.FeatureContext;
import com.aryan.featureflags.sdk.exception.SdkException;
import com.aryan.featureflags.sdk.model.EvaluationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Objects;

public class HttpEvaluator {

    private final SdkConfig sdkConfig;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public HttpEvaluator(SdkConfig sdkConfig) {
        this.sdkConfig = Objects.requireNonNull(
                sdkConfig, "SdkConfig cannot be null"
        );
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public boolean evaluate(String featureKey, FeatureContext context) {
        try {
            // Build URL
            String url = String.format(
                    "%s/api/features/%s/evaluate?env=%s",
                    sdkConfig.getBaseUrl(),
                    featureKey,
                    sdkConfig.getEnvironment()
            );

            // Backend expects { "attributes": { ... } }
            Map<String, Object> payload =
                    Map.of("attributes", context.getAttributes());

            String requestBody =
                    objectMapper.writeValueAsString(payload);

            // Build HTTP request
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json");

            if (sdkConfig.getApiKey() != null) {
                builder.header(
                        "Authorization",
                        "Bearer " + sdkConfig.getApiKey()
                );
            }

            HttpRequest request = builder
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Send request
            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            // Handle HTTP errors
            if (response.statusCode() != 200) {
                throw new SdkException(
                        "Feature evaluation failed with status: " + response.statusCode()
                );
            }

            // ✅ CORRECT JSON DESERIALIZATION
            EvaluationResponse evalResponse =
                    objectMapper.readValue(
                            response.body(),
                            EvaluationResponse.class
                    );

            return evalResponse.isEnabled();

        } catch (Exception e) {
            throw new SdkException(
                    "Failed to evaluate feature flag", e
            );
        }
    }

}
