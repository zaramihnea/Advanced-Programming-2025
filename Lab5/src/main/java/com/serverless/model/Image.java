package com.serverless.model;

import com.serverless.exception.InvalidImageException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record Image(String name, LocalDate date, List<String> tags, String location) {
    public Image {
        try {
            if (name == null || name.isEmpty()) {
                throw new InvalidImageException("Image name cannot be null or empty");
            }
            if (date == null) {
                throw new InvalidImageException("Date cannot be null");
            }
            if (location == null || location.isEmpty()) {
                throw new InvalidImageException("Location cannot be null or empty");
            }
            if (tags == null) {
                tags = new ArrayList<>();
            }
        } catch (InvalidImageException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}