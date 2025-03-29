package com.serverless.command;

import com.serverless.exception.CommandExecutionException;
import com.serverless.exception.ImageDisplayException;
import com.serverless.exception.ImageNotFoundException;
import com.serverless.repository.ImageRepository;

public class DisplayCommand extends AbstractCommand {
    public DisplayCommand(ImageRepository repository) {
        super(repository, "display", "Display an image from the repository",
                "display <name>");
    }

    @Override
    public void execute(String[] args) throws CommandExecutionException {
        validateArgCount(args, 1);

        String name = args[0];

        try {
            repository.displayImage(name);
        } catch (ImageNotFoundException | ImageDisplayException e) {
            throw new CommandExecutionException("Failed to display image: " + e.getMessage(), e);
        }
    }
}