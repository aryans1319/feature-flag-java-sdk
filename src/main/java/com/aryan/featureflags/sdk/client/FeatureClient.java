package com.aryan.featureflags.sdk.client;

import com.aryan.featureflags.sdk.config.SdkConfig;
import com.aryan.featureflags.sdk.context.FeatureContext;
import com.aryan.featureflags.sdk.exception.SdkException;
import com.aryan.featureflags.sdk.transport.HttpEvaluator;

public class FeatureClient {
    /**
     * Evaluates whether a feature is enabled for the given context.
     *
     * Safe failure behavior:
     * - Returns false if evaluation fails for any reason.
     */

    private final HttpEvaluator evaluator;
    public FeatureClient(SdkConfig sdkConfig) {

        this.evaluator = new HttpEvaluator(sdkConfig);
    }
    public boolean isEnabled(String featureKey, FeatureContext context){
        try{
            return evaluator.evaluate(featureKey, context);
        } catch(SdkException e){

            return false;
        }

    }


}
