package com.aryan.featureflags.sdk.config;

import com.aryan.featureflags.sdk.context.FeatureContext;

import java.util.Objects;

public class SdkConfig {



    //    The design goals here are that it should be immutable and user cannot forget required fields
    private final String baseUrl;
    private final String environment;


    public SdkConfig(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.environment = builder.environment;
    }
    public String getBaseUrl() {
        return baseUrl;
    }

    public String getEnvironment() {
        return environment;
    }
//    Builder here acts a temporary mutable object

    public static final class Builder{
        private String baseUrl;
        private String environment;

        public Builder baseUrl(String baseUrl){
            Objects.requireNonNull(baseUrl, "baseUrl cannot be null");
            this.baseUrl= baseUrl;
            return this;
        }
        public Builder environment(String environment){
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
