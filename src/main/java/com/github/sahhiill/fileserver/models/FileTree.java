package com.github.sahhiill.fileserver.models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileTree {

    FileNode root;

    public FileNode getRoot() {
        return root;
    }

    public FileTree(File file) {
        this.root = new FileNode(file);
    }

    class FileNode {
        String name;
        String path;
        boolean isFile;
        List<FileNode> children;

        public FileNode(String name, String path, boolean isFile, List<FileNode> children) {
            this.name = name;
            this.path = path;
            this.isFile = isFile;
            this.children = children;
        }

        public FileNode (File file) {
            this.name = file.getName();
            this.path = file.getPath();
            this.isFile = file.isFile();
            if ((!file.isFile()) && file.listFiles() != null) {
                if (Objects.requireNonNull(file.listFiles()).length == 0) {
                    this.children = null;
                    return;
                }
                File[] files = file.listFiles();
                assert files != null;
                children = new ArrayList<>();
                for (File f : files) {
                    children.add(new FileNode(f));
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

        public String getName() {
            return name;
        }

        public String getPath() {
            return path;
        }

        public boolean isFile() {
            return isFile;
        }

        public List<FileNode> getChildren() {
            return children;
        }
    }

}
