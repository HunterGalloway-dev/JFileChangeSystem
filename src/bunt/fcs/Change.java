package bunt.fcs;

import bunt.files.CodeFile;

public class Change {
	public enum ChangeType {
		CREATE,
		RENAME,
		UPDATE,
		DELETE,
		WRITE_IN_PROGRESS,
		WRITE_COMPLETE
	}
	
	private ChangeType type;
	private CodeFile file;
	private String newName;
	
	public Change(ChangeType type) {
		this.type = type;
	}
	
	public Change(ChangeType type, CodeFile file) {
		this(type);
		this.file = file;
	}
	
	public Change(String newName, CodeFile file) {
		this(ChangeType.RENAME,file);
		this.newName = newName;
	}
	
	public ChangeType getType() {
		return this.type;
	}
	
	public CodeFile getFile() {
		return this.file;
	}
	
	public String getNewName() {
		return this.newName;
	}
}
