package exceptions;

@SuppressWarnings("serial")
public class ArchivoNoEncontradoException extends Error{
	
	public ArchivoNoEncontradoException(String message) {
		super(message);
	}
}
