package com.github.sahhiill.fileserver.models.Service;

import com.github.sahhiill.fileserver.compression.SimpleCompression;
import com.github.sahhiill.fileserver.models.FileNode;
import com.github.sahhiill.fileserver.models.repos.FileNodeRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileNodeService {

    SimpleCompression simpleCompression;
    FileNodeRepo fileNodeRepo;

    public FileNodeService(SimpleCompression simpleCompression, FileNodeRepo fileNodeRepo) {
        this.simpleCompression = simpleCompression;
        this.fileNodeRepo = fileNodeRepo;
    }
    
    public void uploadFile(File file) throws FileNotFoundException {
        FileNode fileNode;
        if (file.isDirectory()) {
            fileNode = new FileNode(file);
        } else {
            String content = simpleCompression.compress(file);
            String[] extension = file.getName().split("\\.");
            fileNode = new FileNode(
                    file.getName(),
                    content,
                    extension[extension.length - 1],
                    true,
                    null
            );
        }
        fileNodeRepo.save(fileNode);
    }

    public void createAFolder(String name) {
        FileNode fileNode = new FileNode(name, null, null, false, null);
        fileNodeRepo.save(fileNode);
    }

    public void uploadFile(MultipartFile file, String path) {

    }


    public FileNode getFileNodeByPath(String path) {
        // deconstruct path
        String[] pathArray = path.split("/");
        Deque<String> pathQueue = new ArrayDeque<>(Arrays.asList(pathArray));
        // get the root
        List<FileNode> root = fileNodeRepo.findByName(pathQueue.getLast());
        if (root.size() == 0) {
            throw new RuntimeException("Root not found");
        }
        // the root in question should match the entire deque of path
        FileNode rootNode = root.get(0);
        pathQueue.removeLast();
        //TODO: iterate through the path queue

        return null;
    }

}
