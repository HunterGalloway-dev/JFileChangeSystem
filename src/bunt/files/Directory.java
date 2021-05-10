package bunt.files;

import java.util.HashMap;
import java.util.Map;

public class Directory extends CodeFile {

    private Map<String, File> files;
    private Map<String, Directory> subDirectories;

    public Directory(String name, String path) {
	super(name, path, FileType.Directory);

	this.files = new HashMap<String, File>();
	this.subDirectories = new HashMap<String, Directory>();
    }
    
    public Directory(String path) {
	super(path);
	
	this.files = new HashMap<String, File>();
	this.subDirectories = new HashMap<String, Directory>();
    }

    public void addFile(File file) {
	this.files.put(file.getName(), file);
    }

    public void addFile(String curPath, File file) {
	int i = curPath.indexOf("/");

	if (i == -1) {
	    addFile(file);
	} else {
	    String dir = curPath.substring(0, i);
	    Directory nextDir = this.subDirectories.get(dir);

	    if (nextDir != null) {
		nextDir.addFile(curPath.substring(i + 1), file);
	    } else {
		/*
		 * No sub directory found need to further generate the directory(s) in curPath
		 * to place file correctly
		 */

		Directory subDir = Directory.createNestedDirectories(curPath);
		i = curPath.indexOf("/");
		curPath = curPath.substring(i+1);
		subDir.addFile(curPath,file);
		this.addSubDirectory(subDir);
	    }
	}
    }

    public CodeFile codeFileFromPath(String curPath) {
	CodeFile ret;

	if (curPath.indexOf(".") == -1) {
	    
	    ret = getDirectory(curPath);
	} else {
	    ret = getFile(curPath);
	}

	return ret;
    }

    public File getFile(String curPath) {
	File ret = null;

	int i = curPath.indexOf("/");

	if (i == -1) {
	    ret = this.files.get(curPath);
	} else {
	    String dirName = curPath.substring(0, i);
	    Directory dir = this.subDirectories.get(dirName);

	    if (dir != null) {
		ret = getFile(curPath.substring(i + 1));
	    } else {
		System.err.println("Error finding sub directory while getting file");
	    }
	}

	return ret;
    }

    public Directory getDirectory(String curPath) {
	Directory ret = null;
	
	int i = curPath.indexOf("/");
	if (i == curPath.length() - 1) {
	    curPath = curPath.substring(0, i);
	    
	    ret = this.subDirectories.get(curPath);
	} else {
	    
	    String dirName = curPath.substring(0, i);
	    Directory dir = this.subDirectories.get(dirName);
	    if (dir != null) {
		ret = dir.getDirectory(curPath.substring(i + 1));
	    } else {
		System.err.println("Error finding sub directory while getting dir");
	    }
	}

	return ret;
    }

    public void updateFile(String path, File newFile) {
	int i = path.indexOf("/");
	if (i == -1) {
	    if (this.files.containsKey(path)) {
		this.files.remove(path);
		String fName = newFile.getName();
		this.files.put(fName, newFile);
	    } else {

	    }
	} else {
	    String directoryName = path.substring(0, i);
	    Directory dir = this.subDirectories.get(directoryName);

	    if (dir != null) {
		dir.updateFile(path.substring(i + 1), newFile);
	    } else {
		System.err.println("Failure to find diretory from path in order to update");
	    }
	}
    }

    public void renameFile(String path, String newName, CodeFile file) {
	int i = path.indexOf("/");

	if (file.getType() == FileType.Directory) {
	    if (i == path.length() - 1) {
		path = path.substring(0, i);
		if (this.subDirectories.containsKey(path)) {
		    this.subDirectories.get(path).setName(newName);
		} else {
		    System.err.println("Failure to find directory to rename");
		}
	    } else {
		String directoryName = path.substring(0, i);
		Directory dir = this.subDirectories.get(directoryName);

		if (dir != null) {
		    dir.renameFile(path.substring(i + 1), newName, file);
		} else {
		    System.err.println("Failure to find diretory from path in order to rename");
		}
	    }
	} else {
	    if (i == -1) {
		if (this.files.containsKey(path)) {
		    this.files.get(path).setName(newName);
		} else {
		    System.err.println("Failure to find file to rename");
		}
	    } else {
		String directoryName = path.substring(0, i);
		Directory dir = this.subDirectories.get(directoryName);

		if (dir != null) {
		    dir.renameFile(path.substring(i + 1), newName, file);
		} else {
		    System.err.println("Failure to find diretory from path in order to rename");
		}
	    }
	}
    }

    public void removeFile(String curPath, CodeFile file) {
	int i = curPath.indexOf("/");

	if (file.getType() == FileType.Directory) {
	    if (i == path.length() - 1) {
		path = path.substring(0, i);
		if (this.subDirectories.containsKey(path)) {
		    this.subDirectories.remove(path);
		} else {
		    System.err.println("Failure to find directory to rename");
		}
	    } else {
		String directoryName = path.substring(0, i);
		Directory dir = this.subDirectories.get(directoryName);

		if (dir != null) {
		    dir.removeFile(path.substring(i + 1), file);
		} else {
		    System.err.println("Failure to find diretory from path in order to rename");
		}
	    }
	} else {
	    if (i == -1) {
		if (this.files.containsKey(curPath)) {
		    this.files.remove(curPath);
		} else {
		    System.err.println("Failure to find file to delete");
		}
	    } else {
		String directoryName = curPath.substring(0, i);
		Directory dir = this.subDirectories.get(directoryName);

		if (dir != null) {
		    dir.removeFile(curPath.substring(i + 1), file);
		} else {
		    System.err.println("Failure to find directory from path to delete");
		}
	    }
	}
    }

    public void addSubDirectory(Directory subDirectory) {
	this.subDirectories.put(subDirectory.getName(), subDirectory);
    }

    public File getFile(File file) {
	return this.files.get(file.getName());
    }

    public Directory getDirectory(Directory directory) {
	return this.subDirectories.get(directory.getName());
    }

    public static Directory createNestedDirectories(String path) {
	Directory dir, curDir;

	int i = path.indexOf("/");
	dir = new Directory(path.substring(0, i+1));
	path = path.substring(i + 1);
	curDir = dir;

	while ((i = path.indexOf("/")) != -1) {
	    Directory tempDir = new Directory(path.substring(0, i+1));
	    path = path.substring(i + 1);
	    curDir.addSubDirectory(tempDir);
	    curDir = tempDir;
	}

	return dir;
    }

    public String prettyPrint(String curTab) {
	String ret = this.name + "\n";
	final String TAB = "  ";

	for (String dirKey : this.subDirectories.keySet()) {
	    ret += curTab + TAB + this.subDirectories.get(dirKey).prettyPrint(curTab + TAB);
	}

	for (String fileKey : this.files.keySet()) {
	    ret += curTab + TAB + this.files.get(fileKey) + "\n";
	}
	
	ret += curTab;

	return ret;
    }

    public String toString() {
	return prettyPrint("");
    }

}
