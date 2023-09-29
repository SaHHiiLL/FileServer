package com.github.sahhiill.fileserver.service;

import com.github.sahhiill.fileserver.compression.SimpleCompression;
import com.github.sahhiill.fileserver.exceptions.FileDoesNotExists;
import com.github.sahhiill.fileserver.exceptions.InvalidFileName;
import com.github.sahhiill.fileserver.models.FileNode;
import com.github.sahhiill.fileserver.models.repos.FileNodeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

@Service
public class FileServiceCompressed {

    private static final Logger log = LoggerFactory.getLogger(FileServiceCompressed.class);
    private final FileNodeRepo fileNodeRepo;

    public FileServiceCompressed(FileNodeRepo fileNodeRepo) {
        this.fileNodeRepo = fileNodeRepo;
    }

    public void uploadFile(MultipartFile file) throws IOException {
        //1. get the file object
        //2. read the file and compress it
        //3. save the file in the database

        if (file.isEmpty()) {
            log.info("File is empty");
            return;
        }

        byte[] bytes = file.getBytes();
        String contents = new SimpleCompression().compress(bytes);
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        fileName = fileName + "." + extension + ".simplecompressed";

        FileNode fileNode = new FileNode(fileName, contents, extension, true, null);
        fileNodeRepo.save(fileNode);
    }

//    private FileNode decodeFilePath(String path) {
//        //1. find each flie the database;
//        //2. return the Node
//
//        List<String> pathList = Arrays.asList(path.split("/"));
//
//    }

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
