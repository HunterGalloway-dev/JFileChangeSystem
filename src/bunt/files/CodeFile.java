package bunt.files;

/**
 * Models what a file will contain: the path for specefied file and the content
 * of that file which will be defined in the subclasses of this parent class
 * 
 * @author bunt
 *
 */
public abstract class CodeFile {

    /**
     * Types that a file can be
     * 
     * @author bunt
     *
     */
    public enum FileType {
	Directory, Java, Other
    }

    /**
     * Relative path to directory code is being compiled in
     */
    protected String path;

    /**
     * FileType of this file
     */
    protected FileType type;
    /**
     * Path of this file
     */
    protected String name;

    /**
     * Constructs a CodeFile with the name and type provided
     * 
     * @param name - Name for this
     * @param type - Type of this
     */
    public CodeFile(String name, FileType type) {
	setName(name);
	this.type = type;
    }

    /**
     * Constructs a CodeFile with the name, type, and path provided
     * 
     * @param name - Name for this
     * @param type - Type of this
     * @param Path - Path for this. Path only gives path to directory without name
     *             so we add it to it. Easier for instaniating
     */
    public CodeFile(String name, String path, FileType type) {
	this.path = path;
	setName(name);
	this.type = type;
    }

    public CodeFile(String path) {
	int i = path.indexOf(".");
	
	String name = path;
	
	if (i == -1) { // Directory
	    while ((i = name.indexOf("/")) != name.length() - 1) {
		name = name.substring(i + 1);
	    }
	} else {
	    while ((i = name.indexOf("/")) != -1) {
		name = name.substring(i + 1);
		
	    }
	}

	i = path.indexOf(name);
	this.path = path.substring(0, i);

	setName(name);
    }

    public static String stripPathToFileName(String path) {
	int i;

	while ((i = path.indexOf("/")) != -1) {
	    path = path.substring(i + 1);
	}

	return path;
    }

    public static String stripPathToDirName(String path) {
	int i;

	while ((i = path.indexOf("/")) != path.length() - 1) {
	    path = path.substring(i + 1);
	}

	return path.substring(0, i);
    }

    public String getPathToFile() {
	return this.path;
    }

    public String getPath() {
	return this.path + name;
    }

    public void setPath(String path) {
	this.path = path;
    }

    /**
     * 
     * @return the path of this
     */
    public String getName() {
	return this.name;
    }

    /**
     * Updates the current path of this
     * 
     * @param path - the new path of this
     * 
     */
    public void setName(String name) {
	this.name = name;

	int i = name.indexOf(".");

	if (i == -1) {
	    this.type = FileType.Directory;
	} else {
	    String ext = name.substring(i + 1);

	    switch (ext) {
		case "java":
		    this.type = FileType.Java;
		    break;
		default:
		    this.type = FileType.Other;
		    break;
	    }
	}
    }

    /**
     * 
     * @return - Type of this
     */
    public FileType getType() {
	return this.type;
    }
    
    public String toString() {
 	return "[" + this.name + "] (" + this.type + ") {" + this.path + "}";
    }
}
