package com.github.sahhiill.fileserver.service;

import com.github.sahhiill.fileserver.Constants;
import org.apache.tomcat.util.http.fileupload.MultipartStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private static final Logger log = LoggerFactory.getLogger(FileService.class);

    public List<File> getAllFiles() {

        String path = Constants.ROOT_FOLDER.orElseThrow(() -> new RuntimeException("Root folder not set"));
        File rootFolder = new File(path);
        File[] files = rootFolder.listFiles();
        if (files == null) {
            log.error("Failed to list files in root folder: " + rootFolder.getPath());
            throw new RuntimeException("Failed to list files in root folder");
        }
        return List.of(files);
    }

    public void createFile(String name) {

        if (name.contains("/")) {
            throw new RuntimeException("Invalid file name");
        }

        String rootPath = Constants.ROOT_FOLDER.orElseThrow(() -> new RuntimeException("Root folder not set"));
        File rootFolder = new File(rootPath);
        File file = new File(rootFolder, name);
        try {
            boolean newFile = file.createNewFile();
            if (!newFile) {
                log.error("Failed to create file: " + file.getPath());
                throw new RuntimeException("Failed to create file");
            }
        } catch (Exception e) {
            log.error("Failed to create file: " + file.getPath());
            throw new RuntimeException("Failed to create file", e);
        }
        log.warn("Created file: " + file.getPath());
    }

    public void deleteFile(String name) {
        if (name.contains("/")) {
            throw new RuntimeException("Invalid file name");
        }

        String rootPath = Constants.ROOT_FOLDER.orElseThrow(() -> new RuntimeException("Root folder not set"));
        File rootFolder = new File(rootPath);
        File file = new File(rootFolder, name);

        if (!file.exists()) {
            log.error("File does not exist: " + file.getPath());
            throw new RuntimeException("File does not exist");
        }

        boolean deleted = file.delete();
        if (!deleted) {
            log.error("Failed to delete file: " + file.getPath());
            throw new RuntimeException("Failed to delete file");
        }
        log.warn("Deleted file: " + file.getPath());
    }

    public void changeName(String name, String newName) {
        if (name.contains("/")) {
            throw new RuntimeException("Invalid file name");
        }

        String rootPath = Constants.ROOT_FOLDER.orElseThrow(() -> new RuntimeException("Root folder not set"));
        File rootFolder = new File(rootPath);
        File file = new File(rootFolder, name);

        if (!file.exists()) {
            log.error("File does not exist: " + file.getPath());
            throw new RuntimeException("File does not exist");
        }

        boolean renamed = file.renameTo(new File(rootFolder, newName));
        if (!renamed) {
            log.error("Failed to rename file: " + file.getPath());
            throw new RuntimeException("Failed to rename file");
        }

        log.info("Renamed file: " + file.getPath() + " to " + newName);
    }

    public File getFile(String name) {
        if (name.contains("/")) {
            throw new RuntimeException("Invalid file name");
        }

        String rootPath = Constants.ROOT_FOLDER.orElseThrow(() -> new RuntimeException("Root folder not set"));
        File rootFolder = new File(rootPath);
        File file = new File(rootFolder, name);

        if (!file.exists()) {
            log.error("File does not exist: " + file.getPath());
            throw new RuntimeException("File does not exist");
        }

        log.info("Got file: " + file.getPath());
        return file;
    }


    public boolean uploadFile(MultipartFile file) {
        String rootPath = Constants.ROOT_FOLDER.orElseThrow(() -> new RuntimeException("Root folder not set"));
        File rootFolder = new File(rootPath);
        File newFile = new File(rootFolder, file.getOriginalFilename());

        try {
            file.transferTo(newFile);
        } catch (Exception e) {
            log.error("Failed to upload file: " + newFile.getPath());
            throw new RuntimeException("Failed to upload file", e);
        }

        log.info("Uploaded file: " + newFile.getPath());
        return true;
    }
}