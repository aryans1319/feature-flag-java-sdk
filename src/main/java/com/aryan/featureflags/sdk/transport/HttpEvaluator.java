package com.aryan.featureflags.sdk.transport;

import com.aryan.featureflags.sdk.config.SdkConfig;
import com.aryan.featureflags.sdk.context.FeatureContext;
import com.aryan.featureflags.sdk.exception.SdkException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpEvaluator {

    private final SdkConfig sdkConfig;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;


    public HttpEvaluator(SdkConfig sdkConfig) {
        this.sdkConfig = sdkConfig;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public boolean evaluate(String featureKey, FeatureContext context) {
        try {
//            build url
            String url = String.format(
                    "%s/api/features/%s/evaluate?env=%s",
                    sdkConfig.getBaseUrl(),
                    featureKey,
                    sdkConfig.getEnvironment()

            );
//            Serializing FeatureContext
            String requestBody = objectMapper.writeValueAsString(context.getAttributes());

//            Building HTTP request

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
//              sending the request
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

//            Handling http errors
            if (response.statusCode() != 200) {
                throw new SdkException(
                        "Feature evaluation failed with status: " + response.statusCode()
                );


            }
            return Boolean.parseBoolean(response.body());

        } catch (Exception e) {

            throw new SdkException("Failed to evaluate feature flag");
        }

    }
}
