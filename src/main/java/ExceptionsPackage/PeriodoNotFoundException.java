package ExceptionsPackage;

@SuppressWarnings("serial")
public class PeriodoNotFoundException extends RuntimeException {

	public PeriodoNotFoundException(String msg) {
		super(msg);
	}
}
