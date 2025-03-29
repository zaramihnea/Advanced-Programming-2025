package com.serverless.app;

import com.serverless.exception.ImageDisplayException;
import com.serverless.exception.ImageNotFoundException;
import com.serverless.exception.InvalidImageException;
import com.serverless.model.Image;
import com.serverless.repository.ImageRepository;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ImageRepository repository = new ImageRepository();
        System.out.println("Image repository created");

        try {
            String projectPath = new File("").getAbsolutePath();

            Image image1 = new Image(
                    "Crocodile",
                    LocalDate.of(2023, 5, 15),
                    List.of("crocodile", "sky", "plane"),
                    projectPath + "/src/photos/bombardiro.png"
            );

            Image image2 = new Image(
                    "Elephant",
                    LocalDate.of(2023, 6, 20),
                    List.of("elephant", "cactus", "desert"),
                    projectPath + "/src/photos/lirili.png"
            );

            repository.addImage(image1);
            repository.addImage(image2);

            try {
                System.out.println("\nDisplaying image:");
                repository.displayImage("Crocodile");
            } catch (ImageNotFoundException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (ImageDisplayException e) {
                System.err.println("Display error: " + e.getMessage());
            }

            try {
                System.out.println("\nAttempting to display non-existent image:");
                repository.displayImage("NonExistentImage");
            } catch (ImageNotFoundException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (ImageDisplayException e) {
                System.err.println("Display error: " + e.getMessage());
            }

        } catch (InvalidImageException e) {
            System.err.println("Invalid image error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}