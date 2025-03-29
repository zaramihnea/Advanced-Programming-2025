package com.serverless.command;

import com.serverless.exception.CommandExecutionException;
import com.serverless.exception.InvalidImageException;
import com.serverless.exception.ImageNotFoundException;
import com.serverless.model.Image;
import com.serverless.repository.ImageRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class AddCommand extends AbstractCommand {
    public AddCommand(ImageRepository repository) {
        super(repository, "add", "Add a new image to the repository",
                "add <name> <date:yyyy-MM-dd> <location> [tag1,tag2,...]");
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

        // Parse tags if provided
        List<String> tags = List.of();
        if (args.length > 3) {
            tags = Arrays.asList(args[3].split(","));
        }

        try {
            Image image = new Image(name, date, tags, location);
            repository.addImage(image);
            System.out.println("Image added successfully: " + name);
        } catch (InvalidImageException e) {
            throw new CommandExecutionException("Failed to add image: " + e.getMessage(), e);
        }
    }
}