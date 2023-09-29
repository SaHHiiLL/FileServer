package com.github.sahhiill.fileserver.models;

import jakarta.persistence.*;

import java.io.File;
import java.io.FileNotFoundException;



@Entity
@Table
public class FileTree {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(nullable = false)
    FileNode root;

    public FileNode getRoot() {
        return root;
    }

    public FileTree(File file) throws FileNotFoundException {
        this.root = new FileNode(file);
    }


}
