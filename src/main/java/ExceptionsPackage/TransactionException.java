package ExceptionsPackage;

public class TransactionException extends RuntimeException {

	public TransactionException(String mensaje) {
		super("La transacción falló: " + mensaje);
	}
}
