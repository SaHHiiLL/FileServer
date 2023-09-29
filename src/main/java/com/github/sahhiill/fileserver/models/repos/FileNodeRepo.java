package com.github.sahhiill.fileserver.models.repos;

import com.github.sahhiill.fileserver.models.FileNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileNodeRepo extends JpaRepository<FileNode, Integer> {


    List<FileNode> findByName(String name);
}
