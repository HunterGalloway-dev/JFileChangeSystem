package bunt.files;

import java.util.HashMap;
import java.util.Map;

import bunt.fcs.Change;
import bunt.fcs.Change.ChangeType;
import bunt.fcs.ChangeListener;
import bunt.files.CodeFile.FileType;

public class FileStructure implements ChangeListener {

    private Directory main;

    private Map<String, File> files;

    public FileStructure() {
	main = new Directory("SESSION_ID/");
	this.files = new HashMap<String, File>();
    }

    public void addFile(String path, File file) {
	this.main.addFile(path, file);

	this.files.put(path, file);
    }

    public CodeFile getFile(String curPath) {
	CodeFile ret;

	if (curPath.indexOf(".") == -1) {
	    ret = main.getDirectory(curPath);
	} else {
	    ret = this.files.get(curPath);
	}

	return ret;
    }

    public void updateFile(String path, File newFile) {
	this.files.get(path).setContent(newFile.getContent());
    }

    public void renameFile(String path, String newName, CodeFile file) {
	if (file.getType() != FileType.Directory) {

	    CodeFile removedFile = this.files.remove(path);
	    removedFile.setName(newName);

	    this.files.put(removedFile.getPath(), (File) removedFile);
	} else {
	    main.renameFile(path, newName, file);
	}

    }

    public void removeFile(String path, CodeFile file) {
	main.removeFile(path, file);

	if (file.getType() != FileType.Directory) {
	    this.files.remove(path);
	}
    }

    public String toString() {
	String ret = "[Directory View] \n" + main;

	ret += "\n[List of All Files] \n";

	for (String path : this.files.keySet()) {
	    ret += path + "\n";
	}

	return ret;
    }

    @Override
    public void change(Change change) {
	ChangeType type = change.getType();
	CodeFile codeFile = change.getFile();

	switch (type) {
	    case CREATE:
		if (codeFile.getType() != FileType.Directory) {
		    File file = (File) codeFile;
		    this.addFile(file.getPath(), file);
		}
		break;
	    case RENAME:
		this.renameFile(codeFile.getPath(), change.getNewName(), codeFile);
		break;
	    case UPDATE:
		File file = (File) codeFile;
		this.updateFile(file.getPath(), file);
		break;
	    case DELETE:
		this.removeFile(codeFile.getPath(), codeFile);
		break;
	    default:
		break;
	}
    }

}
