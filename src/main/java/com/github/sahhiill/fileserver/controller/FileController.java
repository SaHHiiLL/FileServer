package com.github.sahhiill.fileserver.controller;

import com.github.sahhiill.fileserver.models.FileChangeNameRequest;
import com.github.sahhiill.fileserver.models.FileTree;
import com.github.sahhiill.fileserver.service.FileService;
import com.github.sahhiill.fileserver.service.FileServiceCompressed;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("api/v1/file")
public class FileController {

    private final FileService fileService;
    private final FileServiceCompressed fileServiceCompressed;
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    public FileController(FileService fileService, FileServiceCompressed fileServiceCompressed) {
        this.fileService = fileService;
        this.fileServiceCompressed = fileServiceCompressed;
    }



    @GetMapping("/hello")
    public String helloWorld() {
        return "<html> <head></head> <body> Hello </body> </html>";
    }

    @GetMapping
    public List<String> getFiles() {
        // Will only get the files for now no directories.
        return fileService.getAllFiles().stream().filter(File::isFile).map(File::getName).toList();
    }

    @PostMapping
    public void createFile(@RequestBody  String name) {
        fileService.createFile(name);
    }

    @DeleteMapping
    public void deleteFile(@RequestBody String name) {
        fileService.deleteFile(name);
    }

    @PostMapping("/change")
    public void changeName(@RequestBody FileChangeNameRequest request) {
        fileService.changeName(request.getName(), request.getNewName());
    }

    @GetMapping("/download")
    public void downloadFile(@RequestParam("path") String path, HttpServletResponse response) throws IOException {
        File file = new File(path);
        FileSystemResource resource = new FileSystemResource(file);
        InputStream stream = resource.getInputStream();
        response.setContentType("application/octet-stream");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));
        IOUtils.copy(stream, response.getOutputStream());
        response.flushBuffer();
    }

    @PostMapping("/upload")
    public void handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
//        log.info("File name: " + file.getOriginalFilename());
//        log.info("File size: " + file.getSize() + " bytes");
//        File f = fileService.uploadFile(file);
//
//        Scanner myReader = new Scanner(f);
//        SimpleCompression simpleCompression = new SimpleCompression();
//        System.out.println(simpleCompression.compress(file.getBytes()));


        fileServiceCompressed.uploadFile(file);
    }

    @GetMapping("/filetree")
    public FileTree getFileTree() throws FileNotFoundException {
        return fileService.getFileTree();
    }
}
