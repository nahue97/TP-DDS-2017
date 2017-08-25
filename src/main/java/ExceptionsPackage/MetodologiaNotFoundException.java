package ExceptionsPackage;

@SuppressWarnings("serial")
public class MetodologiaNotFoundException extends RuntimeException {
	
	public MetodologiaNotFoundException(String msg) {
		super(msg);
	}
}
