package com.serverless.shell;

import com.serverless.command.Command;
import com.serverless.exception.CommandExecutionException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Shell {
    private final Map<String, Command> commands = new HashMap<>();
    private boolean running = true;
    public void registerCommand(Command command) {
        commands.put(command.getName(), command);
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Image Management System");
        System.out.println("Type 'help' for a list of commands or 'exit' to quit");

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            String[] parts = input.split("\\s+");
            String commandName = parts[0];

            if ("exit".equalsIgnoreCase(commandName)) {
                running = false;
                System.out.println("Goodbye!");
                continue;
            }

            if ("help".equalsIgnoreCase(commandName)) {
                showHelp();
                continue;
            }

            Command command = commands.get(commandName);
            if (command == null) {
                System.out.println("Unknown command: " + commandName);
                System.out.println("Type 'help' for a list of available commands");
                continue;
            }
            String[] args = new String[parts.length - 1];
            System.arraycopy(parts, 1, args, 0, args.length);

            try {
                command.execute(args);
            } catch (CommandExecutionException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        scanner.close();
    }
    private void showHelp() {
        System.out.println("Available commands:");
        System.out.println("------------------");

        commands.values().stream()
                .sorted((c1, c2) -> c1.getName().compareTo(c2.getName()))
                .forEach(command -> {
                    System.out.println(command.getName() + " - " + command.getDescription());
                    System.out.println("    Usage: " + command.getUsage());
                    System.out.println();
                });

        System.out.println("exit - Exit the application");
        System.out.println("help - Show this help message");
    }
}