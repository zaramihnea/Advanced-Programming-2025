package com.serverless.command;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.serverless.exception.CommandExecutionException;
import com.serverless.exception.InvalidImageException;
import com.serverless.model.Image;
import com.serverless.repository.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LoadCommand extends AbstractCommand {
    public LoadCommand(ImageRepository repository) {
        super(repository, "load", "Load the repository from a JSON file",
                "load <filename>");
    }

    @Override
    public void execute(String[] args) throws CommandExecutionException {
        validateArgCount(args, 1);

        String filename = args[0];

        try {
            File file = new File(filename);
            if (!file.exists()) {
                throw new CommandExecutionException("File does not exist: " + filename);
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            List<Image> images = mapper.readValue(file, new TypeReference<List<Image>>() {});

            repository.clear();

            int count = 0;
            for (Image image : images) {
                try {
                    repository.addImage(image);
                    count++;
                } catch (InvalidImageException e) {
                    System.err.println("Skipping invalid image: " + e.getMessage());
                }
            }

            System.out.println("Loaded " + count + " images from " + filename);
        } catch (IOException e) {
            throw new CommandExecutionException("Failed to load repository: " + e.getMessage(), e);
        }
    }
}