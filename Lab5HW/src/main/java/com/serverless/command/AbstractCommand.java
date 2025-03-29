package com.serverless.command;

import com.serverless.exception.CommandExecutionException;
import com.serverless.repository.ImageRepository;

public abstract class AbstractCommand implements Command {
    protected final ImageRepository repository;
    private final String name;
    private final String description;
    private final String usage;

    public AbstractCommand(ImageRepository repository, String name, String description, String usage) {
        this.repository = repository;
        this.name = name;
        this.description = description;
        this.usage = usage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    protected void validateArgCount(String[] args, int expectedCount) throws CommandExecutionException {
        if (args.length != expectedCount) {
            throw new CommandExecutionException(
                    "Invalid number of arguments. Expected " + expectedCount +
                            " but got " + args.length + ".\nUsage: " + getUsage());
        }
    }

    protected void validateMinArgCount(String[] args, int minCount) throws CommandExecutionException {
        if (args.length < minCount) {
            throw new CommandExecutionException(
                    "Not enough arguments. Expected at least " + minCount +
                            " but got " + args.length + ".\nUsage: " + getUsage());
        }
    }
}