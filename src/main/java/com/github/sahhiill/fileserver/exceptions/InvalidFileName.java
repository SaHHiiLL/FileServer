package com.github.sahhiill.fileserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFileName extends RuntimeException {
    private static final String MESSAGE = "Invalid file name %s";
    public InvalidFileName(String fileName) {
        super(String.format(MESSAGE, fileName));
    }
}
