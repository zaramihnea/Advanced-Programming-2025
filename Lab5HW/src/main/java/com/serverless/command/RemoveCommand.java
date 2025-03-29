package com.serverless.command;

import com.serverless.exception.CommandExecutionException;
import com.serverless.exception.ImageNotFoundException;
import com.serverless.repository.ImageRepository;

public class RemoveCommand extends AbstractCommand {
    public RemoveCommand(ImageRepository repository) {
        super(repository, "remove", "Remove an image from the repository",
                "remove <name>");
    }

    @Override
    public void execute(String[] args) throws CommandExecutionException {
        validateArgCount(args, 1);

        String name = args[0];

        try {
            repository.removeImage(name);
            System.out.println("Image removed successfully: " + name);
        } catch (ImageNotFoundException e) {
            throw new CommandExecutionException("Failed to remove image: " + e.getMessage(), e);
        }
    }
}