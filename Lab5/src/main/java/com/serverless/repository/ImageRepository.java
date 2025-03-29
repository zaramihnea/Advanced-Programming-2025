package com.serverless.repository;

import com.serverless.exception.ImageDisplayException;
import com.serverless.exception.ImageNotFoundException;
import com.serverless.exception.InvalidImageException;
import com.serverless.model.Image;
import com.serverless.service.ImageViewer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageRepository {
    private final List<Image> images = new ArrayList<>();
    private final ImageViewer imageViewer = new ImageViewer();

    public void addImage(Image image) throws InvalidImageException {
        if (image == null) {
            throw new InvalidImageException("Image cannot be null");
        }
        if (getImageByName(image.name()) != null) {
            throw new InvalidImageException("An image with the name '" + image.name() + "' already exists");
        }

        images.add(image);
        System.out.println("Added image: " + image.name());
    }

    public Image getImageByName(String name) {
        return images.stream()
                .filter(img -> img.name().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void displayImage(String imageName) throws ImageNotFoundException, ImageDisplayException {
        Image image = getImageByName(imageName);
        if (image == null) {
            throw new ImageNotFoundException("Image not found: " + imageName);
        }

        File file = new File(image.location());
        imageViewer.display(file);
    }

    public List<Image> getAllImages() {
        return new ArrayList<>(images);
    }
}