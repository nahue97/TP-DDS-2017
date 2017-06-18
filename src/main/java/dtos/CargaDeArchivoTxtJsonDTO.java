package dtos;

public class CargaDeArchivoTxtJsonDTO implements DTO{

	private String pathFile="";
	
	public CargaDeArchivoTxtJsonDTO(String pathFile) {
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