package com.aryan.featureflags.sdk.exception;

public class SdkException extends  RuntimeException{

    public SdkException(String message) {
        super(message);
    }

    public SdkException(String message, Throwable cause) {
        super(message, cause);
    }

}
