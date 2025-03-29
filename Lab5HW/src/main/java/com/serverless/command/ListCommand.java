package com.serverless.command;

import com.serverless.exception.CommandExecutionException;
import com.serverless.repository.ImageRepository;

import java.util.List;

public class ListCommand extends AbstractCommand {
    public ListCommand(ImageRepository repository) {
        super(repository, "list", "List all images in the repository",
                "list");
    }

    @Override
    public void execute(String[] args) throws CommandExecutionException {
        validateArgCount(args, 0);

        List<com.serverless.model.Image> images = repository.getAllImages();

        if (images.isEmpty()) {
            System.out.println("Repository is empty");
            return;
        }

        System.out.println("Images in repository:");
        System.out.println("---------------------");

        for (com.serverless.model.Image image : images) {
            System.out.println("Name: " + image.name());
            System.out.println("Date: " + image.date());
            System.out.println("Tags: " + String.join(", ", image.tags()));
            System.out.println("Location: " + image.location());
            System.out.println("---------------------");
        }
    }
}