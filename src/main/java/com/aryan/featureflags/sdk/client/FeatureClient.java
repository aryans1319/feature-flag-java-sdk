package com.aryan.featureflags.sdk.client;

import com.aryan.featureflags.sdk.config.SdkConfig;
import com.aryan.featureflags.sdk.context.FeatureContext;
import com.aryan.featureflags.sdk.transport.HttpEvaluator;

public class FeatureClient {

    private final SdkConfig sdkConfig;
    private final HttpEvaluator evaluator;
    public FeatureClient(SdkConfig sdkConfig, HttpEvaluator evaluator) {
        this.sdkConfig = sdkConfig;
        this.evaluator = evaluator;
    }
    public boolean isEnabled(String featureKey, FeatureContext context){
        return evaluator.evaluate(featureKey, context);
    }


}
