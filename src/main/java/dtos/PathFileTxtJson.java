package dtos;

public class PathFileTxtJson implements PathFile{

	private String pathFile="";
	
	public PathFileTxtJson(String pathFile) {
		super();
		this.pathFile = pathFile;
	}

	public void setPathFile(String location) {
		pathFile = location;
	}
	
	public String getPathFile() {
		return pathFile;
	}
}