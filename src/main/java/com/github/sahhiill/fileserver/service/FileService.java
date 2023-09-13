package com.github.sahhiill.fileserver.service;

import com.github.sahhiill.fileserver.Constants;
import com.github.sahhiill.fileserver.exceptions.CannotRenameFile;
import com.github.sahhiill.fileserver.exceptions.FileDoesNotExists;
import com.github.sahhiill.fileserver.exceptions.InvalidFileName;
import com.github.sahhiill.fileserver.models.FileTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class FileService {

    private final Constants constants;
    private static final Logger log = LoggerFactory.getLogger(FileService.class);

    public FileService(Constants constants) {
        this.constants = constants;
    }

    public List<File> getAllFiles() {

        String path = constants.getRootFolder();
        File rootFolder = new File(path);
        File[] files = rootFolder.listFiles();
        if (files == null) {
            log.error("Failed to list files in root folder: " + rootFolder.getPath());
            throw new RuntimeException("Failed to list files in root folder");
        }
        return List.of(files);
    }

    public void createFile(String name) {

        File file = getFile(name, constants.getRootFolder());
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
        File file = getFile(name, constants.getRootFolder());

        if (!file.exists()) {
            throw new FileDoesNotExists(name);
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

        String rootPath = constants.getRootFolder();
        File rootFolder = new File(rootPath);
        File file = new File(rootFolder, name);

        boolean renamed = file.renameTo(new File(rootFolder, newName));
        if (!renamed) {
            throw new CannotRenameFile(name, newName);
        }

        log.info("Renamed file: " + file.getPath() + " to " + newName);
    }



    public void uploadFile(MultipartFile file) {
        File newFile = getFile(file.getOriginalFilename(), constants.getRootFolder());

        try {
            file.transferTo(newFile);
        } catch (Exception e) {
            log.error("Failed to upload file: " + newFile.getPath());
            throw new RuntimeException("Failed to upload file", e);
        }

        log.info("Uploaded file: " + newFile.getPath());
    }

    public FileTree getFileTree() {
        String rootPath = constants.getRootFolder();
        File rootFolder = new File(rootPath);
        return new FileTree(rootFolder);
    }

    private void checkFileName(String name) {
        if (name.contains("/")) {
            throw new InvalidFileName(name);
        }
    }

    private File getFile(String name, String rootPath) {
        checkFileName(name);
        File rootFolder = new File(rootPath);
        File file = new File(rootFolder, name);

        if (!file.exists()) {
            throw new FileDoesNotExists(name);
        }

        return file;
    }
}