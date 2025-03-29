package com.serverless.command;

import com.serverless.exception.CommandExecutionException;

public interface Command {
    void execute(String[] args) throws CommandExecutionException;
    String getName();
    String getDescription();
    String getUsage();
}