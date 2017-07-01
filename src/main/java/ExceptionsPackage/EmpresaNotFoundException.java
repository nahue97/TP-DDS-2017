package ExceptionsPackage;

public class EmpresaNotFoundException extends RuntimeException {
	public EmpresaNotFoundException(String msg) {
		super(msg);
	}
}
