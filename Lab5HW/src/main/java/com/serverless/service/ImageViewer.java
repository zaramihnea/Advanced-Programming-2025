package com.serverless.service;

import com.serverless.exception.ImageDisplayException;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageViewer {
    public void display(File file) throws ImageDisplayException {
        try {
            if (!file.exists()) {
                throw new ImageDisplayException("File does not exist: " + file.getAbsolutePath());
            }
            if (!file.canRead()) {
                throw new ImageDisplayException("File cannot be read: " + file.getAbsolutePath());
            }

            System.out.println("Opening image: " + file.getAbsolutePath());

            if (!Desktop.isDesktopSupported()) {
                System.out.println("Desktop is not supported - cannot open file automatically");
                return;
            }
            Desktop.getDesktop().open(file);

        } catch (IOException e) {
            throw new ImageDisplayException("Error opening file: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ImageDisplayException("Error displaying image: " + e.getMessage(), e);
        }
    }
}