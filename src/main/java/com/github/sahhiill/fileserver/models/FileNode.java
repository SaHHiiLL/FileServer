package com.github.sahhiill.fileserver.models;

import com.github.sahhiill.fileserver.compression.SimpleCompression;
import jakarta.persistence.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class FileNode {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = true)
    String content;

    @Column(nullable = true)
    String extension;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    boolean isFile;

    @OneToMany
    @JoinColumn(nullable = true)
    List<FileNode> children;


    public FileNode() {
    }

    public FileNode(String name,
                    String content,
                    String extension,
                    boolean isFile,
                    List<FileNode> children) {
        this.content = content;
        this.extension = extension;
        this.name = name;
        this.isFile = isFile;
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public FileNode(Integer id,
                    String name,
                    String content,
                    String extension,
                    boolean isFile,
                    List<FileNode> children) {
        this.id = id;
        this.content = content;
        this.extension = extension;
        this.name = name;
        this.isFile = isFile;
        this.children = children;
    }

    public FileNode(File file) throws FileNotFoundException {
        this.name = file.getName();
        this.isFile = file.isFile();

        if ((!file.isFile()) && file.listFiles() != null) {
            File[] files = file.listFiles();
            assert files != null;
            children = new ArrayList<FileNode>();
            for (File f : files) {
                if (f.isDirectory()) {
                    children.add(new FileNode(f));
                } else {
                    this.extension = f.getName().substring(f.getName().lastIndexOf(".") + 1);
                    this.content = new SimpleCompression().compress(f);
                    children.add(new FileNode(f.getName(), this.content, this.extension,true,null));
                }
            }
        } else {
            this.children = null;
        }
    }

    public void addChild(FileNode child) {
        if (this.isFile()) {
            throw new RuntimeException("Cannot add child to file");
        }

        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public boolean isFile() {
        return isFile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsFile(boolean b) {
        this.isFile = b;
    }

    public void setExtension(String o) {
        this.extension =  o;
    }

    public void setContent(String o) {
        this.content = o;
    }
}
