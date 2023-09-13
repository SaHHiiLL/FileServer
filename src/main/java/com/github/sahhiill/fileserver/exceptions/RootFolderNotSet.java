package com.github.sahhiill.fileserver.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class RootFolderNotSet extends RuntimeException {
    private static final String MESSAGE = "Root folder not set";
    private static final Logger log = LoggerFactory.getLogger(RootFolderNotSet.class);
    public RootFolderNotSet() {
        super(MESSAGE);
        log.error(MESSAGE + " - exiting");
        System.exit(1);
    }
}
