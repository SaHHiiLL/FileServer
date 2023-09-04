package com.github.sahhiill.fileserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.util.Optional;

@SpringBootApplication
public class FileServerApplication {

    private static final Logger log = LoggerFactory.getLogger(FileServerApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(FileServerApplication.class, args);
    }

    @Bean
    public void RootFolder() {
        String path = "/home/sahil/api-data/files";
        // Create the root folder if it doesn't exist
        File rootFolder = new File(path);
        if (!rootFolder.exists()) {
            log.info("Creating root folder: " + path);
            boolean mkdirs = rootFolder.mkdirs();
            if (!mkdirs) {
                log.error("Failed to create root folder: " + path);
                System.exit(1);
                return;
            }
        }
        log.info("Root folder at -> " + rootFolder.getPath());
        Constants.ROOT_FOLDER = Optional.of(rootFolder.getPath());
    }
}
