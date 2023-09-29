package com.github.sahhiill.fileserver.models.repos;

import com.github.sahhiill.fileserver.models.FileTree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileTreeRepo extends JpaRepository<FileTree, Integer> {
}
