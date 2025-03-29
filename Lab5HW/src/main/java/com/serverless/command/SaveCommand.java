package com.serverless.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.serverless.exception.CommandExecutionException;
import com.serverless.model.Image;
import com.serverless.repository.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SaveCommand extends AbstractCommand {
    public SaveCommand(ImageRepository repository) {
        super(repository, "save", "Save the repository to a JSON file",
                "save <filename>");
    }

    @Override
    public void execute(String[] args) throws CommandExecutionException {
        validateArgCount(args, 1);

        String filename = args[0];

        try {
            List<Image> images = repository.getAllImages();

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            mapper.writeValue(new File(filename), images);

            System.out.println("Repository saved to " + filename);
        } catch (IOException e) {
            throw new CommandExecutionException("Failed to save repository: " + e.getMessage(), e);
        }
    }
}