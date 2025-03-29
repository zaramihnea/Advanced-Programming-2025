package com.serverless.command;

import com.serverless.exception.CommandExecutionException;
import com.serverless.model.Image;
import com.serverless.repository.ImageRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportCommand extends AbstractCommand {
    private final Configuration freemarkerConfig;

    public ReportCommand(ImageRepository repository) {
        super(repository, "report", "Generate an HTML report of the repository",
                "report [filename]");

        freemarkerConfig = new Configuration(Configuration.VERSION_2_3_30);
        freemarkerConfig.setClassLoaderForTemplateLoading(
                getClass().getClassLoader(), "templates");
        freemarkerConfig.setDefaultEncoding("UTF-8");
    }

    @Override
    public void execute(String[] args) throws CommandExecutionException {
        String filename = "repository-report.html";
        if (args.length > 0) {
            filename = args[0];
        }

        List<Image> images = repository.getAllImages();

        try {
            Map<String, Object> model = new HashMap<>();
            model.put("images", images);
            model.put("repositorySize", images.size());
            model.put("generationDate", java.time.LocalDateTime.now().toString());

            Template template = freemarkerConfig.getTemplate("report-template.ftl");

            File outputFile = new File(filename);
            try (Writer writer = new FileWriter(outputFile)) {
                template.process(model, writer);
            }

            System.out.println("Report generated: " + outputFile.getAbsolutePath());

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(outputFile);
            } else {
                System.out.println("Desktop operations not supported - cannot open report automatically");
            }

        } catch (IOException | TemplateException e) {
            throw new CommandExecutionException("Failed to generate report: " + e.getMessage(), e);
        }
    }
}