package parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.uqbar.commons.model.UserException;

public class AnalizadorSintactico {
	static AnalizadorIndicador parser = null;
	
	public void chequearIndicador(String sentence) {
                sentence = "(" + sentence + ")";
                InputStream is = new ByteArrayInputStream(sentence.getBytes());
                if(parser == null) parser = new AnalizadorIndicador(is);
                else AnalizadorIndicador.ReInit(is);
                try
                {
                  switch (AnalizadorIndicador.start())
                  {
                    case 0 :
                    	throw new UserException("Expresion correcta");
                    default :
                    break;
                  }
                }
                catch (Exception e)
                {
                	throw new UserException("Error en la expresion");
                }
                catch (Error e)
                {
                	throw new UserException("Error en la expresion");
                }
                finally
                {
                  
                }
        	}
};
