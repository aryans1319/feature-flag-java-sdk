package com.aryan.featureflags.sdk.client;

import com.aryan.featureflags.sdk.config.SdkConfig;
import com.aryan.featureflags.sdk.context.FeatureContext;
import com.aryan.featureflags.sdk.exception.SdkException;
import com.aryan.featureflags.sdk.transport.HttpEvaluator;

import java.util.Objects;

public class FeatureClient {

    /**
     * Evaluates whether a feature is enabled for the given context.
     *
     * Safe failure behavior:
     * - Returns false if evaluation fails for any reason
     * - Never throws exceptions to user code
     */
    private final HttpEvaluator evaluator;

    public FeatureClient(SdkConfig sdkConfig) {
        Objects.requireNonNull(sdkConfig, "SdkConfig cannot be null");
        this.evaluator = new HttpEvaluator(sdkConfig);
    }

    public boolean isEnabled(String featureKey, FeatureContext context) {

        // Fail fast on invalid inputs
        if (featureKey == null || featureKey.isBlank()) {
            return false;
        }

        if (context == null) {
            return false;
        }

        try {
            return evaluator.evaluate(featureKey, context);
        } catch (SdkException e) {
            // SDK must never crash user applications
            return false;
        }
    }
}
