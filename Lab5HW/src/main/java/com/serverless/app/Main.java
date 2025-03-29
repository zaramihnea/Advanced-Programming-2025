package com.serverless.app;

import com.serverless.command.*;
import com.serverless.repository.ImageRepository;
import com.serverless.shell.Shell;

public class Main {
    public static void main(String[] args) {
        ImageRepository repository = new ImageRepository();

        Shell shell = new Shell();

        shell.registerCommand(new AddCommand(repository));
        shell.registerCommand(new RemoveCommand(repository));
        shell.registerCommand(new UpdateCommand(repository));
        shell.registerCommand(new DisplayCommand(repository));
        shell.registerCommand(new ListCommand(repository));
        shell.registerCommand(new SaveCommand(repository));
        shell.registerCommand(new LoadCommand(repository));
        shell.registerCommand(new ReportCommand(repository));

        shell.run();
    }
}