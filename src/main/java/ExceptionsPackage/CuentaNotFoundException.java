package ExceptionsPackage;

public class CuentaNotFoundException extends RuntimeException {

	public CuentaNotFoundException(String msg) {
		super(msg);
	}
}
