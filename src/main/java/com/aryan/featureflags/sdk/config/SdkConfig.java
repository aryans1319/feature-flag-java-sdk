package com.aryan.featureflags.sdk.config;

import com.aryan.featureflags.sdk.context.FeatureContext;

import java.util.Objects;

public class SdkConfig {



    //    The design goals here are that it should be immutable and user cannot forget required fields
    private final String baseUrl;
    private final Environment environment;


    private SdkConfig(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.environment = builder.environment;
    }
    public String getBaseUrl() {
        return baseUrl;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public enum Environment{
        DEV,
        STAGING,
        PROD
    }
//    Builder here acts a temporary mutable object

    public static final class Builder{
        private String baseUrl;
        private Environment environment;

        public Builder baseUrl(String baseUrl){
            Objects.requireNonNull(baseUrl, "baseUrl cannot be null");
            if (!baseUrl.startsWith("http")) {
                throw new IllegalArgumentException(
                        "baseUrl must start with http or https"
                );
            }
            this.baseUrl= baseUrl;
            return this;
        }
        public Builder environment(Environment environment){
            Objects.requireNonNull(environment, "environment cannot be null");
            this.environment= environment;
            return this;
        }

        public SdkConfig build(){
            Objects.requireNonNull(baseUrl, "baseUrl must be set");
            Objects.requireNonNull(environment," environment must be set" );
            return new SdkConfig(this);
        }


    }
}
