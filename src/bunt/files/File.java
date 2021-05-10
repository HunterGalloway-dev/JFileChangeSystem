package bunt.files;

/**
 * Model a file such as a file you would find on a computer 
 * @author bunt
 *
 */
public class File extends CodeFile {
	
	/**
	 * Content of the file
	 */
	private String content;
	
	/**
	 * Constructs a CodeFile object to represent a file on a computer
	 * @param name - Path of file
	 * @param type - Type of file (can not be directory)
	 * @param content - Content of the file
	 */
	public File(String name, FileType type, String content) {
		super(name, type);
		setContent(content);
	}
	
	public File(String name, String path, FileType type, String content) {
		super(name, path, type);
		setContent(content);
	}
	
	public File(String path, String content) {
	    super(path);
	    setContent(content);
	}
	
	/**
	 * 
	 * @return content of this
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * Updates the content of this
	 * @param content - the new string we want this.content to be equal to
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	public String toString() {
		return super.toString() + " " + this.content;
	}
}
