package com.github.sahhiill.fileserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CannotRenameFile extends RuntimeException  {
    private static final String MESSAGE = "Cannot rename file %s to %s";
    public CannotRenameFile(String fileName, String newName) {
        super(String.format(MESSAGE, fileName, newName));
    }
}
