package com.github.sahhiill.fileserver.models.Service;

import com.github.sahhiill.fileserver.compression.SimpleCompression;
import com.github.sahhiill.fileserver.models.FileNode;
import com.github.sahhiill.fileserver.models.repos.FileNodeRepo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;

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

//    public FileNode getFileNodeByPath(String path) {
//        // deconstruct path
//        String[] pathArray = path.split("/");
//        String name = pathArray[pathArray.length - 1];
//        String parentPath = path.substring(0, path.length() - name.length() - 1);
//
//    }

}
