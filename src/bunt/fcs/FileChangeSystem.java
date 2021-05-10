package bunt.fcs;

import java.util.LinkedList;
import java.util.List;
import bunt.files.FileStructure;
import bunt.fcs.Change.ChangeType;
import bunt.files.CodeFile;
import bunt.files.File;

public class FileChangeSystem {

    private List<ChangeListener> listeners;
    private FileStructure fileStruct;

    public FileChangeSystem() {
	this.listeners = new LinkedList<ChangeListener>();
	this.fileStruct = new FileStructure();
	addListener(this.fileStruct);
    }

    public void addListener(ChangeListener listener) {
	this.listeners.add(listener);
    }

    public void changeFiles(Change change) {
	for (ChangeListener changeListener : listeners) {
	    changeListener.change(change);
	}
    }
    
    public CodeFile getFile(String curPath) {
	return this.fileStruct.getFile(curPath);
    }
    
    public void addFile(String path, String content) {
	File file = new File(path,content);
	
	addFile(file);
    }

    public void addFile(File file) {
	Change fcCreate = new Change(ChangeType.CREATE, file);
	this.changeFiles(fcCreate);
    }
    
    public void updateFile(String path, String newContent) {
	File file = (File) this.fileStruct.getFile(path);
	
	updateFile(file,newContent);
    }
    
    public void updateFile(File file, String newContent) {
	file.setContent(newContent);
	
	Change fcUpdate = new Change(ChangeType.UPDATE, file);
	
	this.changeFiles(fcUpdate);
    }
    
    public void renameFile(String path, String newName) {
	CodeFile file = this.fileStruct.getFile(path);
	
	renameFile(file, newName);
    }
    
    public void renameFile(CodeFile file, String newName) {
	Change fcRename = new Change(newName, file);
	
	this.changeFiles(fcRename);
    }
    
    public void removeFile(String path) {
	CodeFile file = this.fileStruct.getFile(path);
	
	removeFile(file);
    }
    
    public void removeFile(CodeFile file) {
	Change fcRemove = new Change(ChangeType.DELETE, file);
	
	this.changeFiles(fcRemove); 
    }

    public String toString() {
	return this.fileStruct.toString();
    }
}
