package com.github.sahhiill.fileserver.controller;

import com.github.sahhiill.fileserver.models.FileChangeNameRequest;
import com.github.sahhiill.fileserver.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("api/v1/file")
public class FileController {

    private final FileService fileService;
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    public FileController(FileService fileService) {
        this.fileService = fileService;
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

    @GetMapping("/download/{name}")
    public void downloadFile(@PathVariable("name") String name, HttpServletResponse response) throws IOException {
        File file = fileService.getFile(name);
        FileSystemResource resource = new FileSystemResource(file);
        InputStream stream = resource.getInputStream();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        IOUtils.copy(stream, response.getOutputStream());
        response.flushBuffer();
    }

    @PostMapping("/upload")
    public void handleFileUpload(@RequestParam("file") MultipartFile file)  {
        log.info("File uploaded");
        fileService.uploadFile(file);
    }
}
