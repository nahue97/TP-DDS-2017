package ExceptionsPackage;

@SuppressWarnings("serial")
public class RutaDeArchivoInvalidaExeption extends Error{
	
	public RutaDeArchivoInvalidaExeption(String mensaje){
		super(mensaje);
	}
}
