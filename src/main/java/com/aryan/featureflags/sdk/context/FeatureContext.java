package com.aryan.featureflags.sdk.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * FeatureContext represents the evaluation context sent to the backend.
 * It is an immutable container of attributes such as userId, country, etc.
 *
 * SDK does NOT interpret these attributes.
 * Backend evaluation engine decides how to use them.
 */
public final class FeatureContext {

    private final Map<String, Object> attributes;

    private FeatureContext(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    /**
     * Returns an unmodifiable map of attributes.
     * Used internally by the SDK transport layer.
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for FeatureContext.
     */
    public static final class Builder {

        private final Map<String, Object> attributes = new HashMap<>();

        /**
         * Sets the stable user identifier.
         * This is REQUIRED for percentage rollout.
         */
        public Builder userId(String userId) {
            Objects.requireNonNull(userId, "userId cannot be null");
            attributes.put("userId", userId);
            return this;
        }

        /**
         * Adds a custom attribute used for rule evaluation.
         */
        public Builder attribute(String key, Object value) {
            Objects.requireNonNull(key, "attribute key cannot be null");
            Objects.requireNonNull(value, "attribute value cannot be null");
            attributes.put(key, value);
            return this;
        }

        /**
         * Builds an immutable FeatureContext.
         */
        public FeatureContext build() {
            return new FeatureContext(
                    Collections.unmodifiableMap(new HashMap<>(attributes))
            );
        }
    }
}
