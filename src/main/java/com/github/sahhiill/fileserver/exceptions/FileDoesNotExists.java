package com.github.sahhiill.fileserver.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileDoesNotExists extends RuntimeException {
    private static final String MESSAGE = "File %s does not exists";
    public FileDoesNotExists(String fileName) {
        super(String.format(MESSAGE, fileName));
    }
}
