async function getFileTree() {
    const response = await fetch('/api/v1/file/filetree');
    const root = await response.json().then(data => data.root);

    // mapping it to a FileNode
    const rootFileNode = new FileNode(root.file, root.name, root.path, root.children);
    const element = document.getElementById("root")

    // flattening the tree to a list of file names
    let files = [];
    walkTree(files, rootFileNode, element);
}

function walkTree(list, node, element) {
    for (let i = 0; i < node.children.length; i++) {
        if (!node.children[i].isFile) {
            let li = document.createElement("li");
            let details = document.createElement("details");
            let summary = document.createElement("summary");
            let ul = document.createElement("ul");

            walkTree(list, node.children[i], ul);

            summary.append(node.children[i].name);
            details.append(summary);
            details.append(ul);
            li.append(details);
            element.append(li);
        } else {
            let li = document.createElement("li");
            let a = document.createElement("a");
            a.setAttribute("href", "/api/v1/file/download?path=" + node.children[i].path);
            a.append(node.children[i].name);
            li.append(a);
            element.append(li);
        }
    }
    return list;
}

class FileNode {
    constructor(isFile, name, path, children) {
        if (children) {
            this.children = children.map(child => new FileNode(child.file, child.name, child.path, child.children));
        } else {
            this.children = undefined;
        }
        this.isFile = isFile;
        this.name = name;
        this.path = path;
    }
}