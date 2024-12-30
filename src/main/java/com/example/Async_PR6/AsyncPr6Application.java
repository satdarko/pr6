package com.example.Async_PR6;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableScheduling
public class AsyncPr6Application {

    public static void main(String[] args) {
        SpringApplication.run(AsyncPr6Application.class, args);
    }
}

@Component
class FileUpdater {

    private static final Path FILE_PATH = Path.of("counter.txt");
    private int counter = 0;

    @Scheduled(fixedRate = 10000)  // Запуск кожні 10 секунд
    public void updateFile() throws IOException {
        counter += 5;
        String content = "Counter: " + counter + "\n";
        Files.writeString(FILE_PATH, content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        System.out.println("File counter.txt updated with: " + counter);
    }
}

@Component
class DelayedMessagePrinter implements CommandLineRunner {

    @Override
    public void run(String... args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> System.out.println("5 секунд від запуску програми"), 5, 5, TimeUnit.SECONDS);
    }
}
