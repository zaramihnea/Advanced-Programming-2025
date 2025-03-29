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

    public void removeImage(String name) throws ImageNotFoundException {
        Image image = getImageByName(name);
        if (image == null) {
            throw new ImageNotFoundException("Image not found: " + name);
        }

        images.remove(image);
        System.out.println("Removed image: " + name);
    }

    public void updateImage(String name, Image newImage) throws ImageNotFoundException, InvalidImageException {
        if (newImage == null) {
            throw new InvalidImageException("New image cannot be null");
        }

        Image oldImage = getImageByName(name);
        if (oldImage == null) {
            throw new ImageNotFoundException("Image not found: " + name);
        }

        if (!name.equals(newImage.name())) {
            if (getImageByName(newImage.name()) != null) {
                throw new InvalidImageException("An image with the name '" + newImage.name() + "' already exists");
            }
        }

        images.remove(oldImage);
        images.add(newImage);

        System.out.println("Updated image: " + name);
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

    public void clear() {
        images.clear();
        System.out.println("Repository cleared");
    }
}