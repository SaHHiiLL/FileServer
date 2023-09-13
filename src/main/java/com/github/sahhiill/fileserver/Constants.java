package com.github.sahhiill.fileserver;

import com.github.sahhiill.fileserver.exceptions.RootFolderNotSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class Constants {

    private static Optional<String> ROOT_FOLDER = Optional.empty();

    private long MAX_FILE_SIZE = 1024 * 1024 * 10; // 10 MB

    public long getMaxFileSize() {
        return MAX_FILE_SIZE;
    }

    public String getRootFolder() {
        return ROOT_FOLDER.orElseThrow(RootFolderNotSet::new);
    }

    public void setRootFolder(String rootFolder) {
        ROOT_FOLDER = Optional.of(rootFolder);
    }
}
