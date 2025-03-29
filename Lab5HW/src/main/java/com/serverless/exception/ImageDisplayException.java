package com.serverless.exception;

public class ImageDisplayException extends Exception {
    public ImageDisplayException(String message) {
        super(message);
    }

    public ImageDisplayException(String message, Throwable cause) {
        super(message, cause);
    }
}