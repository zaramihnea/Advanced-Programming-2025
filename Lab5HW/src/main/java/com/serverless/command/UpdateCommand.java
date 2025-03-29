package com.serverless.command;

import com.serverless.exception.CommandExecutionException;
import com.serverless.exception.ImageNotFoundException;
import com.serverless.exception.InvalidImageException;
import com.serverless.model.Image;
import com.serverless.repository.ImageRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class UpdateCommand extends AbstractCommand {
    public UpdateCommand(ImageRepository repository) {
        super(repository, "update", "Update an image in the repository",
                "update <name> <date:yyyy-MM-dd> <location> [tag1,tag2,...]");
    }

    @Override
    public void execute(String[] args) throws CommandExecutionException {
        validateMinArgCount(args, 3);

        String name = args[0];
        LocalDate date;
        try {
            date = LocalDate.parse(args[1], DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new CommandExecutionException("Invalid date format. Use yyyy-MM-dd", e);
        }

        String location = args[2];

        List<String> tags = List.of();
        if (args.length > 3) {
            tags = Arrays.asList(args[3].split(","));
        }

        try {
            Image newImage = new Image(name, date, tags, location);
            repository.updateImage(name, newImage);
            System.out.println("Image updated successfully: " + name);
        } catch (InvalidImageException | ImageNotFoundException e) {
            throw new CommandExecutionException("Failed to update image: " + e.getMessage(), e);
        }
    }
}