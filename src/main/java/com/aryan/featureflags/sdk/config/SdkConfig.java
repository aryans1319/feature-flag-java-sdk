package com.aryan.featureflags.sdk.config;

import java.util.Objects;

public final class SdkConfig {

    private final String baseUrl;
    private final String environment;
    private final String apiKey;

    private SdkConfig(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.environment = builder.environment;
        this.apiKey = builder.apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getApiKey() {
        return apiKey;
    }

    public static final class Builder {
        private String baseUrl;
        private String environment;
        private String apiKey;

        public Builder baseUrl(String baseUrl) {
            Objects.requireNonNull(baseUrl, "baseUrl cannot be null");
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder environment(String environment) {
            Objects.requireNonNull(environment, "environment cannot be null");
            this.environment = environment;
            return this;
        }

        public Builder apiKey(String apiKey) {
            Objects.requireNonNull(apiKey, "apiKey cannot be null");
            if (apiKey.isBlank()) {
                throw new IllegalArgumentException("apiKey cannot be blank");
            }
            this.apiKey = apiKey;
            return this;
        }

        public SdkConfig build() {
            Objects.requireNonNull(baseUrl, "baseUrl must be set");
            Objects.requireNonNull(environment, "environment must be set");
            Objects.requireNonNull(apiKey, "apiKey must be set"); // ✅ REQUIRED

            return new SdkConfig(this);
        }
    }
}
