package com.aryan.featureflags.sdk.client;

import com.aryan.featureflags.sdk.config.SdkConfig;

public class FeatureClient {

    private final SdkConfig sdkConfig;

    public FeatureClient(SdkConfig sdkConfig) {
        this.sdkConfig = sdkConfig;
    }
}
