package com.github.sahhiill.fileserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class Constants {

    public static Optional<String> ROOT_FOLDER = Optional.empty();

    private long MAX_FILE_SIZE = 1024 * 1024 * 10; // 10 MB

    public long getMaxFileSize() {
        return MAX_FILE_SIZE;
    }
}
