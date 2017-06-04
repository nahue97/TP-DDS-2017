package ExceptionsPackage;

public class RutaDeArchivoInvalidaException extends RuntimeException {

	public RutaDeArchivoInvalidaException(String mensaje) {
		super(mensaje);
	}
}
